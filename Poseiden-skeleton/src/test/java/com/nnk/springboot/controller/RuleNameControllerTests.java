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
public class RuleNameControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste ruleName")
    public void getRuleNameListPageTest() throws Exception {
	mockMvc.perform(get("/ruleName/list"))
	.andExpect(view().name("ruleName/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout ruleName")
    public void getRuleNameAddPageTest() throws Exception {
	mockMvc.perform(get("/ruleName/add"))
	.andExpect(view().name("ruleName/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour ruleName")
    public void getRuleNameEditPageTest() throws Exception {
	mockMvc.perform(get("/ruleName/edit/1"))
	.andExpect(view().name("ruleName/update"))
	.andExpect(status().isOk());
    }

}
