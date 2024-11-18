package org.hkmadao.tcdt.conf.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.conf.security.tokenmgr.ITokenManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class TokenLogoutHandler implements LogoutHandler {

    private ITokenManager tokenManager;

    public TokenLogoutHandler(ITokenManager memoryTokenManager) {
        this.tokenManager = memoryTokenManager;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(ITokenManager.TCDT_TOKEN);
        if (token != null) {
            try {
                tokenManager.removeToken(token);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}