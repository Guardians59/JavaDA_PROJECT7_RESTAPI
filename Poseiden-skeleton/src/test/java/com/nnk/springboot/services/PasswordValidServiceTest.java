package com.nnk.springboot.services;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PasswordValidServiceTest {
    
    @Autowired
    IPasswordValidService passwordValidService;
    
    @Test
    @Order(1)
    public void passwordValidServiceTest() {
	//GIVEN
	boolean result;
	String password = "Azerty2&";
	//WHEN
	result = passwordValidService.isPasswordValid(password);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    @Order(2)
    public void passwordValidServiceErrorTest() {
	//GIVEN
	boolean result;
	String password = "azerty12&";
	//WHEN
	result = passwordValidService.isPasswordValid(password);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    @Order(3)
    public void passwordValidServiceErrorTwoTest() {
	//GIVEN
	boolean result;
	String password = "Azerty12";
	//WHEN
	result = passwordValidService.isPasswordValid(password);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    @Order(4)
    public void passwordValidServiceErrorThreeTest() {
	//GIVEN
	boolean result;
	String password = "Azerty&a";
	//WHEN
	result = passwordValidService.isPasswordValid(password);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    @Order(5)
    public void passwordValidServiceErrorFourTest() {
	//GIVEN
	boolean result;
	String password = "Aze1&";
	//WHEN
	result = passwordValidService.isPasswordValid(password);
	//THEN
	assertEquals(result, false);
    }

}
