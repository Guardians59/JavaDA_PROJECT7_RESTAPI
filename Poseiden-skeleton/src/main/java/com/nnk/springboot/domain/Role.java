package com.nnk.springboot.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");
    
private final String displayValue;
    
    private Role(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String getAuthority() {
	return name();
    }
}
