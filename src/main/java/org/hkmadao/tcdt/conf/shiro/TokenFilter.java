//package org.hkmadao.tcdt.conf.shiro;
//
//import org.hkmadao.tcdt.modules.user.controller.UserController;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class TokenFilter extends AuthenticatingFilter {
//
//    private static final String TCDT_TOKEN = "TCDT-Token";
//
//
//    /**
//     * 创建Token, 支持自定义Token
//     */
//    @Override
//    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//        String token = this.getToken((HttpServletRequest) servletRequest);
//        if (ObjectUtils.isEmpty(token)) {
//            return null;
//        }
//        return new ShiroToken(token);
//    }
//
//    /**
//     * 兼容跨域
//     */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String token = this.getToken(request);
//        if (!StringUtils.hasLength(token)) {
//            unauthorized(request, response, "未登录！");
//            return false;
//        }
//        //TODO 验证token是否正确
//        if (!UserController.token.equals(token)) {
//            unauthorized(request, response, "token不存在或过期！");
//            return false;
//        }
//
//        // 根据token获取用户信息，会执行 TokenRealm#doGetAuthenticationInfo 方法
//        return executeLogin(servletRequest, servletResponse);
//    }
//
//    /**
//     * 无权限返回
//     *
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    private void unauthorized(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException {
//        response.setContentType("application/json;charset=utf-8");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.getWriter().print(msg);
//    }
//
//    /**
//     * 获取token
//     * 优先从header获取
//     * 如果没有，则从parameter获取
//     *
//     * @param request request
//     * @return token
//     */
//    private String getToken(HttpServletRequest request) {
//        String token = request.getHeader(TCDT_TOKEN);
//        if (ObjectUtils.isEmpty(token)) {
//            token = request.getParameter(TCDT_TOKEN);
//        }
//        return token;
//    }
//}