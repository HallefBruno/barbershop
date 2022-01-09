package com.barber.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/vendor/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/show/**").permitAll()
                .antMatchers("/login-cliente/**").permitAll()
                .antMatchers("/novo-agendamento/**").permitAll()
        .and()
            .authorizeRequests()
                .antMatchers("/dashboard/**").hasRole("DASHBOARD")
                .antMatchers("/cliente-sistema/**").hasRole("SUPER_USER")
                .antMatchers("/usuario/**").hasRole("MANTER_USUARIO")
                .anyRequest().authenticated()
        .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
        .and()
            .logout().deleteCookies("JSESSIONID")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .invalidateHttpSession(true)
        .and()
            .sessionManagement()
            .invalidSessionUrl("/login")
        .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        .and()
            .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
        .and()
            .rememberMe().rememberMeParameter("remember-me");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}


//.and().formLogin()
//.loginPage("/login")
//.usernameParameter("email")
//.passwordParameter("password")
//.defaultSuccessUrl("/default")
//.failureUrl("/login?error").permitAll()