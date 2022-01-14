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
public class TradeControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste trade")
    public void getTradeListPageTest() throws Exception {
	mockMvc.perform(get("/trade/list"))
	.andExpect(view().name("trade/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout trade")
    public void getTradeAddPageTest() throws Exception {
	mockMvc.perform(get("/trade/add"))
	.andExpect(view().name("trade/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour trade")
    public void getTradeEditPageTest() throws Exception {
	mockMvc.perform(get("/trade/edit/1"))
	.andExpect(view().name("trade/update"))
	.andExpect(status().isOk());
    }

}
