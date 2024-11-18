package org.hkmadao.tcdt.conf.security;

import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.conf.CommonResult;
import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;
import org.hkmadao.tcdt.conf.security.info.LoginUserReq;
import org.hkmadao.tcdt.conf.security.info.TcdtUserDetails;
import org.hkmadao.tcdt.conf.security.tokenmgr.ITokenManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private ITokenManager tokenManager;

    public TokenLoginFilter(AuthenticationManager authenticationManager, ITokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            LoginUserReq user = mapper.readValue(req.getInputStream(), LoginUserReq.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        TcdtUserDetails user = (TcdtUserDetails) auth.getPrincipal();
        TcdtUserInfo tcdtUserInfo = user.getTcdtUserInfo();

        TcdtUserInfo tcdtUserInfoReturn = new TcdtUserInfo();
        tcdtUserInfoReturn.setUsername(tcdtUserInfo.getUsername());
        tcdtUserInfoReturn.setPassword(null);
        tcdtUserInfoReturn.setFgActive(tcdtUserInfo.getFgActive());
        tcdtUserInfoReturn.setNickName(tcdtUserInfo.getNickName());
        tcdtUserInfoReturn.setToken(tcdtUserInfo.getToken());
        tcdtUserInfoReturn.setCreateTime(tcdtUserInfo.getCreateTime());
        tcdtUserInfoReturn.setExpiredTime(tcdtUserInfo.getExpiredTime());

        Calendar calendar = Calendar.getInstance();
        tcdtUserInfoReturn.setCreateTime(calendar.getTime());
        System.out.println(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        tcdtUserInfoReturn.setExpiredTime(calendar.getTime());
        System.out.println(calendar.getTime());
        try {
            String token = tokenManager.createToken(tcdtUserInfoReturn);
            tcdtUserInfoReturn.setToken(token);

            ObjectMapper mapper = new ObjectMapper();
            res.setStatus(HttpStatus.OK.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
            mapper.writeValue(res.getWriter(), tcdtUserInfoReturn);
        } catch (BusinessException e) {
            e.printStackTrace();
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setContentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8");
            res.getWriter().write("生成token失败！");
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper mapper = new ObjectMapper();
        CommonResult rm = new CommonResult(401);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), rm);

    }
}
