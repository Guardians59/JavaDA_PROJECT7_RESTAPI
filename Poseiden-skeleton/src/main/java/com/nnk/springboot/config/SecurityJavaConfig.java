package com.nnk.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationConverter;

import com.nnk.springboot.services.impl.UserServiceImpl;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    UserServiceImpl userServiceImpl;
    
    /*@Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
	authBuilder.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username=?")
		.authoritiesByUsernameQuery("SELECT username, role FROM user WHERE username=?");
    }*/
    
    @Bean
    public DaoAuthenticationProvider daoAuth() {
	DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
	auth.setUserDetailsService(userServiceImpl);
	auth.setPasswordEncoder(encoder());
	return auth;
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.antMatchers("/css/**").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.usernameParameter("username")
		.passwordParameter("password")
		.failureUrl("/login?error")
		.defaultSuccessUrl("/bidList/list")
		.and()
		.logout()
		.logoutSuccessUrl("/login?logout").permitAll()
		.and()
		.oauth2Login()
		.loginPage("/login").permitAll()
		.failureUrl("/login?error")
		.defaultSuccessUrl("/bidList/list")
		.and()
		.logout()
		.logoutSuccessUrl("/login?logout").permitAll();
    }
}
