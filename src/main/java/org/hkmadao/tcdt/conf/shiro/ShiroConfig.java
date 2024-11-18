//package org.hkmadao.tcdt.conf.shiro;
//
//import org.apache.shiro.authc.credential.CredentialsMatcher;
//import org.apache.shiro.mgt.SessionsSecurityManager;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.filter.mgt.DefaultFilter;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import jakarta.servlet.Filter;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class ShiroConfig {
//
//    @Bean
//    public TokenRealm tokenRealm() {
//        TokenRealm realm = new TokenRealm();
//        realm.setCredentialsMatcher(credentialsMatcher());
//        return realm;
//    }
//
//    @Bean(name = "shiroFilterFactoryBean")
//    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
//        // 认证过滤器
//        Map<String, Filter> filterMap = new HashMap<>();
//        filterMap.put("token", new TokenFilter());
////        filterMap.put(DefaultFilter.authcBearer.name(), new BearerHttpAuthenticationFilter());
//
//        // 忽略路径
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/user/login", DefaultFilter.anon.name());
//        filterChainDefinitionMap.put("/user/logout", DefaultFilter.anon.name());
//        //下载相关
//        filterChainDefinitionMap.put("/component/generateFull", DefaultFilter.anon.name());
//        filterChainDefinitionMap.put("/component/generatePart", DefaultFilter.anon.name());
//        filterChainDefinitionMap.put("/plan/generateFull", DefaultFilter.anon.name());
//        filterChainDefinitionMap.put("/plan/generatePart", DefaultFilter.anon.name());
//        filterChainDefinitionMap.put("/dtoEntityCollection/generateFull", DefaultFilter.anon.name());
//        filterChainDefinitionMap.put("/dtoEntityCollection/generatePart", DefaultFilter.anon.name());
////        filterChainDefinitionMap.put("/**", "token");
//        filterChainDefinitionMap.put("/**", DefaultFilter.anon.name());
////        filterChainDefinitionMap.put("/**", DefaultFilter.authcBearer.name());
//
//        // 装配bean
//        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
//        filterFactoryBean.setSecurityManager(securityManager());
//        filterFactoryBean.setFilters(filterMap);
//        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
////        filterFactoryBean.setUnauthorizedUrl("/user/unauthorized");
//        return filterFactoryBean;
//    }
//
//    @Bean
//    public CredentialsMatcher credentialsMatcher() {
//        return new TokenCredentialsMatcher();
//    }
//
//    @Bean
//    public SessionsSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(tokenRealm());
//        return securityManager;
//    }
//
//    /**
//     * 没有这个注解，controller使用shiro注解RequiresPermissions会报404
//     * @return
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//}
