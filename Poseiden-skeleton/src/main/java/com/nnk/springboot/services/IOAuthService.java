package com.nnk.springboot.services;

import java.security.Principal;

import com.nnk.springboot.domain.LoggedUsername;

public interface IOAuthService {
    
    public LoggedUsername getUsername(Principal user);
    
    public LoggedUsername getOauthUsername(Principal user);

}
