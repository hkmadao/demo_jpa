package org.hkmadao.tcdt.conf.security;

import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;
import org.hkmadao.tcdt.conf.security.tokenmgr.ITokenManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private ITokenManager tokenManager;

    public TokenAuthenticationFilter(AuthenticationManager authManager, ITokenManager tokenManager) {
        super(authManager);
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // 从header里获取token
        String token = req.getHeader(ITokenManager.TCDT_TOKEN);
        if (!StringUtils.hasLength(token)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              Authentication authResult) throws IOException {
        super.onSuccessfulAuthentication(request, response, authResult);
        //TODO 认证成功，判断token剩余有效时长，重置token有效时间
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从header里获取token
        String token = request.getHeader(ITokenManager.TCDT_TOKEN);
        if (token != null && !"".equals(token.trim())) {
            // parse the token.
            TcdtUserInfo tcdtUserInfo = null;
            try {
                tcdtUserInfo = tokenManager.getTcdtUserInfoByToken(token);
            } catch (BusinessException e) {
                e.printStackTrace();
                //获取用户信息失败
                return null;
            }

            if (!tokenManager.validateToken(token)) {
                //token过期，或者token无效
                return null;
            }

            if (Objects.nonNull(tcdtUserInfo)) {
//                Date expiredTime = tcdtUserInfo.getExpiredTime();
//                if(Calendar.getInstance().after(expiredTime)){
//                    //token过期
//                    return null;
//                }
                return new UsernamePasswordAuthenticationToken(tcdtUserInfo.getUsername(), token, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
