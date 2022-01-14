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
public class BidListControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste bidList")
    public void getBidListListPageTest() throws Exception {
	mockMvc.perform(get("/bidList/list"))
	.andExpect(view().name("bidList/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout bidList")
    public void getBidListAddPageTest() throws Exception {
	mockMvc.perform(get("/bidList/add"))
	.andExpect(view().name("bidList/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour bidList")
    public void getBidListEditPageTest() throws Exception {
	mockMvc.perform(get("/bidList/edit/20"))
	.andExpect(view().name("bidList/update"))
	.andExpect(status().isOk());
    }

}
