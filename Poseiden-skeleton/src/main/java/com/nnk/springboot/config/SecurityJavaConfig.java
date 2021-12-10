package com.nnk.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
	authBuilder.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username=?")
		.authoritiesByUsernameQuery("SELECT username, role FROM user WHERE username=?");
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin().permitAll()
		.and()
		.logout();
    }
}
