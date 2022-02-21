package com.nnk.springboot.services.impl;

import java.security.Principal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.services.IOAuthService;
@Service
public class OAuthServiceImpl implements IOAuthService{

    @Override
    public LoggedUsername getUsername(Principal user) {
	LoggedUsername logged = new LoggedUsername();
	logged.setUsername(user.getName());
	return logged;
    }

    @Override
    public LoggedUsername getOauthUsername(Principal user) {
	LoggedUsername logged = new LoggedUsername();
	String userLogin = null;
	try {
	OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
	if (authToken.isAuthenticated()) {
	userLogin = (String) (authToken.getPrincipal()).getAttributes().get("login");
	logged.setUsername(userLogin);
	}}
	catch(ClassCastException e) {
	}
	return logged;
	}
    }


