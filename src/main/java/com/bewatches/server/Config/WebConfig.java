package com.bewatches.server.Config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity  httpSecurity) throws Exception{
        httpSecurity.authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated();
    }
}
