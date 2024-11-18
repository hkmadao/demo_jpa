package org.hkmadao.tcdt.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
public class CommonBean {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private TcdtPasswordEncoder tcdtPasswordEncoder;

    /**
     * 身份验证Bean
     * 传入获取用户信息的bean & 密码加密器
     * 可以回看一下SecurityConfiguration中 AuthenticationProvider的配置,使用的就是这里注入到容器中的Bean
     * 这个bean 主要是用于用户登录时的身份验证,当我们登录的时候security会帮我们调用这个bean的authenticate方法
     */
    @Bean
    public AuthenticationManager authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //设置获取用户信息的bean
        authProvider.setUserDetailsService(userDetailsService);
        //设置密码加密器
        authProvider.setPasswordEncoder(tcdtPasswordEncoder);

        return new ProviderManager(authProvider);
    }
}
