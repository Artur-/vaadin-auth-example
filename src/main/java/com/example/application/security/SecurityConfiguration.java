package com.example.application.security;

import java.util.Collections;
import java.util.List;

import com.example.application.DataGenerator;
import com.example.application.security.vaadinspring.VaadinWebSecurityConfigurerAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(new User(DataGenerator.HARDCODED_USER1, "{noop}" + DataGenerator.HARDCODED_USER1,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_user"))));
        manager.createUser(new User(DataGenerator.HARDCODED_USER2, "{noop}" + DataGenerator.HARDCODED_USER2,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_admin"))));
        return manager;
    }

    @Override
    protected void configureURLAccess(
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry,
            HttpSecurity http) throws Exception {
        urlRegistry.requestMatchers(new AntPathRequestMatcher("/public-java")).permitAll();
        urlRegistry.requestMatchers(new AntPathRequestMatcher("/public-ts")).permitAll();
        super.configureURLAccess(urlRegistry, http);
    }

    @Override
    protected List<String> getPublicResources() {
        List<String> resources = super.getPublicResources();
        resources.add("/images/*.jpg");
        return resources;
    }
}