package com.nnk.springboot.domain;

public enum Role {
    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");
    
private final String displayValue;
    
    private Role(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }

}
