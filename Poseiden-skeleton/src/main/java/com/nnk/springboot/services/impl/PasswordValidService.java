package com.nnk.springboot.services.impl;

import org.springframework.stereotype.Service;

import com.nnk.springboot.services.IPasswordValidService;

/**
 * La classe PasswordValidService est l'implémentation de l'interface IPasswordValidService.
 * 
 * @see IPasswordValidService
 * @author Dylan
 *
 */
@Service
public class PasswordValidService implements IPasswordValidService{

    @Override
    public boolean isPasswordValid(String password) {
	/*
	 * On instancie le regexp souhaité afin de vérifier que le mot de passe
	 * contient bien un minimum de 8 caractères avec, une majuscule, un chiffre
	 * et un symbole.
	 */
	String regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	return password.matches(regexp);
    }

}
