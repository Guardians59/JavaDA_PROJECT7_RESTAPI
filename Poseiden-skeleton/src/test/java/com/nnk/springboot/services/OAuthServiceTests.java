package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.nnk.springboot.domain.LoggedUsername;

@SpringBootTest
public class OAuthServiceTests {

    @Autowired
    IOAuthService oauthService;
    
    private OAuth2User principalOAuth;
    private Collection<GrantedAuthority> authorities;
    private String authorizedClientRegistrationId;

    @Test
    @DisplayName("Test récupération du nom d'utilisateur en base de donnée")
    public void getUsernameTest() {
	//GIVEN
	Principal principal = Mockito.mock(Principal.class);
	//WHEN
	Mockito.when(principal.getName()).thenReturn("user test");
	LoggedUsername logged = oauthService.getUsername(principal);
	//THEN
	assertEquals(logged.getUsername(), "user test");
    }
    
    @Test
    @DisplayName("Test récupération du login OAuth")
    public void getOAuthUsername() {
	//GIVEN
	Principal user = Mockito.mock(Principal.class);
	principalOAuth = Mockito.mock(OAuth2User.class);
	authorities = Collections.emptyList();
	authorizedClientRegistrationId = "client-registration-1";
	Map<String,Object> map = new HashMap<>();
	map.put("login", "Guardians59");
	OAuth2AuthenticationToken token = Mockito.mock(OAuth2AuthenticationToken.class);
	//WHEN
	Mockito.when(token.getAuthorities()).thenReturn(authorities);
	Mockito.when(token.getAuthorizedClientRegistrationId()).thenReturn(authorizedClientRegistrationId);
	Mockito.when(token.getPrincipal()).thenReturn(principalOAuth);
	Mockito.when(token.getPrincipal().getAttributes()).thenReturn(map);
	Mockito.when(token.isAuthenticated()).thenReturn(true);
	Mockito.when(token.getName()).thenReturn("Guardians59");
	user = (Principal) token;
	LoggedUsername logged = oauthService.getOauthUsername(user);
	//THEN
	assertEquals(logged.getUsername(), "Guardians59");
    }
    
    @Test
    @DisplayName("Test du catch de l'exception du cast")
    public void getExceptionOAuthUsername() {
	//GIVEN
	Principal user = Mockito.mock(Principal.class);
	principalOAuth = Mockito.mock(OAuth2User.class);
	authorities = Collections.emptyList();
	authorizedClientRegistrationId = "client-registration-1";
	Map<String,Object> map = new HashMap<>();
	map.put("login", "Guardians59");
	OAuth2AuthenticationToken token = Mockito.mock(OAuth2AuthenticationToken.class);
	//WHEN
	Mockito.when(token.getAuthorities()).thenReturn(authorities);
	Mockito.when(token.getAuthorizedClientRegistrationId()).thenReturn(authorizedClientRegistrationId);
	Mockito.when(token.getPrincipal()).thenReturn(principalOAuth);
	Mockito.when(token.getPrincipal().getAttributes()).thenReturn(map);
	Mockito.when(token.isAuthenticated()).thenReturn(true);
	Mockito.when(token.getName()).thenReturn("Guardians59");
	LoggedUsername logged = oauthService.getOauthUsername(user);
	//THEN
	assertEquals(logged.getUsername(), null);
    }
}
