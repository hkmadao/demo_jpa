//package org.hkmadao.tcdt.conf.shiro;
//
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//public class TokenRealm extends AuthorizingRealm {
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof ShiroToken;
//    }
//
//    /**
//     * 授权
//     *
//     * @param principalCollection
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        ShiroToken shiroToken = (ShiroToken) principalCollection.getPrimaryPrincipal();
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        //TODO 查询token所关联的用户及权限信息
//        List<String> permissionList = Arrays.asList("add", "update");
//        // 放入权限信息
//        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));
//
//        return authorizationInfo;
//    }
//
//    /**
//     * 身份验证
//     *
//     * @param authenticationToken
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        ShiroToken token = (ShiroToken) authenticationToken;
//        //直接透传token到doGetAuthorizationInfo
//        return new SimpleAuthenticationInfo(token, "1", getName());
//    }
//}