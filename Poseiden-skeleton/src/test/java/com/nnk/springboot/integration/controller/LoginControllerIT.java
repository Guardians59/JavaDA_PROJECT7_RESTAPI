package com.nnk.springboot.integration.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIT {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;
    
    @BeforeEach
    public void setup() {
       mockMvc = MockMvcBuilders
       .webAppContextSetup(context)
       .apply(springSecurity())
       .build();
    }
    
    @Test
    @DisplayName("Test de connexion avec des identifiants valident")
    public void userLoginTest() throws Exception {
       mockMvc.perform(formLogin("/login").user("user test")
	       .password("Azerty12&"))
       	       .andExpect(authenticated());
    }
    
    @Test
    @DisplayName("Test de connexion avec des identifiants invalident")
    public void userLoginErrorTest() throws Exception {
       mockMvc.perform(formLogin("/login").user("user test")
	       .password("error"))
       	       .andExpect(unauthenticated());
    }

}
