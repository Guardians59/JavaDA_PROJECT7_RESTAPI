package com.nnk.springboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste user")
    public void getUserListPageTest() throws Exception {
	mockMvc.perform(get("/user/list"))
	.andExpect(view().name("user/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout user")
    public void getUserAddPageTest() throws Exception {
	mockMvc.perform(get("/user/add"))
	.andExpect(view().name("user/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour user")
    public void getUserEditPageTest() throws Exception {
	mockMvc.perform(get("/user/edit/14"))
	.andExpect(view().name("user/update"))
	.andExpect(status().isOk());
    }

}
