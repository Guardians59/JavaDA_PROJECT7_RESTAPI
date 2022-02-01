package com.nnk.springboot.services.impl;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.nnk.springboot.services.IOAuthService;
@Service
public class OAuthServiceImpl implements IOAuthService{

    @Override
    public StringBuffer getUsernamePasswordLoginInfos(Principal user) {
	StringBuffer usernameInfos = new StringBuffer();
	UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
	
	if(token.isAuthenticated()) {
	    //User userAuthenticated = (User) token.getPrincipal();
	    
	}
	return usernameInfos
	;
    }

    @Override
    public StringBuffer getOauthLoginInfos(Principal user) {
	// TODO Auto-generated method stub
	return null;
    }

}
