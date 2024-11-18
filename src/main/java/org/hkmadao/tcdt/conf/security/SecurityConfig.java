package org.hkmadao.tcdt.conf.security;

import org.hkmadao.tcdt.conf.security.tokenmgr.ITokenManager;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${tcdt.sys.authorized}")
    private boolean fgAuthorized;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource(name = "dbTokenManager")
    private ITokenManager tokenManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if (fgAuthorized) {
            http.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint())
                    .and().cors().configurationSource(corsConfigurationSource()).
                    and().csrf().disable()
                    .authorizeHttpRequests()
                    //登录方法
                    .requestMatchers("/login").permitAll()
                    //静态页面
                    .requestMatchers("/tcdt/**").permitAll()
                    //下载相关
                    .requestMatchers("/billForm/generate").permitAll()
                    .requestMatchers("/component/generateFull").permitAll()
                    .requestMatchers("/component/generatePart").permitAll()
                    .requestMatchers("/entityCollection/generateFull").permitAll()
                    .requestMatchers("/entityCollection/generatePart").permitAll()
                    .requestMatchers("/dtoEntityCollection/generateFull").permitAll()
                    .requestMatchers("/dtoEntityCollection/generatePart").permitAll()
                    .anyRequest().authenticated()
                    .and().logout().logoutUrl("/logout")
                    .addLogoutHandler(new TokenLogoutHandler(tokenManager))
                    .and()
//                    .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager))
//                    .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager))
                    //添加jwt过滤器
                    .addFilterBefore(new TokenLoginFilter(authenticationManager, tokenManager),
                            UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new TokenAuthenticationFilter(authenticationManager, tokenManager),
                            BasicAuthenticationFilter.class)
                    .httpBasic();
        } else {
            http.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint())
                    .and().cors().configurationSource(corsConfigurationSource()).
                    and().csrf().disable()
                    //登录方法
                    .authorizeHttpRequests().requestMatchers("/login").permitAll()
                    //静态页面
                    .requestMatchers("/tcdt/**").permitAll()
                    .requestMatchers("/**").permitAll()
                    .and().logout().logoutUrl("/logout")
                    .addLogoutHandler(new TokenLogoutHandler(tokenManager))
                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                    .and()
                    //关闭session存储
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
//                    .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager))
//                    .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager))
                    //添加jwt过滤器
                    .addFilterBefore(new TokenLoginFilter(authenticationManager, tokenManager),
                            UsernamePasswordAuthenticationFilter.class)
                    //跳过权限认证
//                    .addFilterBefore(new TokenAuthenticationFilter(authenticationManager, tokenManager),
//                            BasicAuthenticationFilter.class)
                    .httpBasic();
        }
        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
