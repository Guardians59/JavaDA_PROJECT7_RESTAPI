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
public class RatingControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste rating")
    public void getRatingListPageTest() throws Exception {
	mockMvc.perform(get("/rating/list"))
	.andExpect(view().name("rating/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout rating")
    public void getRatingAddPageTest() throws Exception {
	mockMvc.perform(get("/rating/add"))
	.andExpect(view().name("rating/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour rating")
    public void getRatingEditPageTest() throws Exception {
	mockMvc.perform(get("/rating/edit/1"))
	.andExpect(view().name("rating/update"))
	.andExpect(status().isOk());
    }

}
