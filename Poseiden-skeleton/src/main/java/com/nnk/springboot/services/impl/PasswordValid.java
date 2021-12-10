package com.nnk.springboot.services.impl;

import org.springframework.stereotype.Service;

import com.nnk.springboot.services.IPasswordValid;

@Service
public class PasswordValid implements IPasswordValid{

    @Override
    public boolean isPasswordValid(String password) {
	String regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	return password.matches(regexp);
    }

}
