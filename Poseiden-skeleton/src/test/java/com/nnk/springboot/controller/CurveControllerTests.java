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
public class CurveControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste curve")
    public void getCurveListPageTest() throws Exception {
	mockMvc.perform(get("/curvePoint/list"))
	.andExpect(view().name("curvePoint/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout curve")
    public void getCurveAddPageTest() throws Exception {
	mockMvc.perform(get("/curvePoint/add"))
	.andExpect(view().name("curvePoint/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour curve")
    public void getCurveEditPageTest() throws Exception {
	mockMvc.perform(get("/curvePoint/edit/12"))
	.andExpect(view().name("curvePoint/update"))
	.andExpect(status().isOk());
    }

}
