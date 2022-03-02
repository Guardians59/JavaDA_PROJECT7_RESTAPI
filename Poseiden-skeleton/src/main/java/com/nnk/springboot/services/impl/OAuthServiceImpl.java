package com.nnk.springboot.services.impl;

import java.security.Principal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.services.IOAuthService;

/**
 * La classe OAuthServiceImpl est l'implémentation du service IOAuthService.
 * 
 * @see IOAuthService
 * @author Dylan
 *
 */
@Service
public class OAuthServiceImpl implements IOAuthService{

    @Override
    public LoggedUsername getUsername(Principal user) {
	/*
	 * On récupère l'username de l'utilisateur connecté depuis la base de
	 * données via le principal.
	 */
	LoggedUsername logged = new LoggedUsername();
	logged.setUsername(user.getName());
	return logged;
    }

    @Override
    public LoggedUsername getOauthUsername(Principal user) {
	LoggedUsername logged = new LoggedUsername();
	String userLogin = null;
	/*
	 * On essaie de caster le principal en token Oauth afin de récupérer les
	 * informations de connexion de l'utilisateur Oauth, sachant que si la
	 * connexion ne s'est pas fait via Oauth le cast echouera c'est pourquoi
	 * nous capturons l'exception ClassCast si le try echoue.
	 * Si le cast est valide, et que la connexion est authentifiée, on récupère
	 * le login de la connexion oauth de l'utilisateur afin de l'ajouter à
	 * notre LoggedUsername.
	 */
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


