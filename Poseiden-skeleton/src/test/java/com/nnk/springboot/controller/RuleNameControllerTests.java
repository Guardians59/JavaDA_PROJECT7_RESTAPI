package com.nnk.springboot.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IRuleNameService ruleNameService;

    @BeforeEach
    public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste ruleName")
    public void getRuleNameListPageTest() throws Exception {
	//GIVEN
	List<RuleName> list = new ArrayList<>();
	RuleName rule = new RuleName();
	rule.setId(20);
	rule.setName("Name test");
	rule.setDescription("Description test");
	list.add(rule);
	//WHEN
	when(ruleNameService.getAllRuleName()).thenReturn(list);
	mockMvc.perform(get("/ruleName/list"))
	//THEN
	.andExpect(view().name("ruleName/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout ruleName")
    public void getRuleNameAddPageTest() throws Exception {
	//WHEN
	mockMvc.perform(get("/ruleName/add"))
	//THEN
	.andExpect(view().name("ruleName/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'ajout ruleName")
    public void addRuleNamePageTest() throws Exception {
	//GIVEN
	RuleName rule = new RuleName();
	rule.setId(21);
	rule.setName("Name test");
	rule.setDescription("Description test");
	//WHEN
	when(ruleNameService.addRuleName(rule)).thenReturn(true);
	mockMvc.perform(post("/ruleName/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("ruleName", rule))
	//THEN
	.andExpect(view().name("ruleName/list"))
	.andExpect(model().attributeExists("success"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de l'ajout ruleName")
    public void addErrorRuleNamePageTest() throws Exception {
	//GIVEN
	RuleName rule = new RuleName();
	rule.setId(21);
	rule.setName("Name test");
	rule.setDescription("Description test");
	//WHEN
	when(ruleNameService.addRuleName(rule)).thenReturn(false);
	mockMvc.perform(post("/ruleName/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("ruleName", rule))
	//THEN
	.andExpect(view().name("ruleName/add"))
	.andExpect(model().attributeExists("error"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de l'ajout ruleName")
    public void addBindingErrorRuleNamePageTest() throws Exception {
	//GIVEN
	RuleName rule = new RuleName();
	rule.setId(21);
	rule.setName("");
	rule.setDescription("Description test");
	//WHEN
	when(ruleNameService.addRuleName(rule)).thenReturn(false);
	mockMvc.perform(post("/ruleName/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("ruleName", rule))
	//THEN
	.andExpect(view().name("ruleName/add"))
	.andExpect(model().attributeDoesNotExist("success"))
	.andExpect(model().attributeDoesNotExist("error"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour ruleName")
    public void getRuleNameEditPageTest() throws Exception {
	//GIVEN
	RuleName rule = new RuleName();
	rule.setId(21);
	rule.setName("Name test");
	rule.setDescription("Description test");
	//WHEN
	when(ruleNameService.getRuleNameById(21)).thenReturn(rule);
	mockMvc.perform(get("/ruleName/edit/21"))
	//THEN
	.andExpect(view().name("ruleName/update"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la mis à jour ruleName")
    public void updateRuleNamePageTest() throws Exception {
	//GIVEN
	RuleName rule = new RuleName();
	rule.setId(21);
	rule.setName("Name test");
	rule.setDescription("Description test");
	//WHEN
	when(ruleNameService.updateRuleName(21, rule)).thenReturn(true);
	mockMvc.perform(post("/ruleName/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("ruleName", rule))
	//THEN
	.andExpect(view().name("ruleName/list"))
	.andExpect(model().attributeExists("updateSuccess"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de la mis à jour ruleName")
    public void updateErrorRuleNamePageTest() throws Exception {
	//GIVEN
	RuleName oldRule = new RuleName();
	oldRule.setId(21);
	oldRule.setName("Name test");
	oldRule.setDescription("Description test");
	RuleName rule = new RuleName();
	rule.setId(21);
	rule.setName("error");
	rule.setDescription("Description test");
	//WHEN
	when(ruleNameService.getRuleNameById(21)).thenReturn(oldRule);
	when(ruleNameService.updateRuleName(21, rule)).thenReturn(false);
	mockMvc.perform(post("/ruleName/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("ruleName", rule))
	//THEN
	.andExpect(view().name("ruleName/update"))
	.andExpect(model().attributeExists("updateError"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de la mis à jour ruleName")
    public void updateBindingErrorRuleNamePageTest() throws Exception {
	//GIVEN
	RuleName oldRule = new RuleName();
	oldRule.setId(21);
	oldRule.setName("Name test");
	oldRule.setDescription("Description test");
	RuleName rule = new RuleName();
	rule.setId(21);
	rule.setName("");
	rule.setDescription("Description test");
	//WHEN
	when(ruleNameService.getRuleNameById(21)).thenReturn(oldRule);
	when(ruleNameService.updateRuleName(21, rule)).thenReturn(false);
	mockMvc.perform(post("/ruleName/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("ruleName", rule))
	//THEN
	.andExpect(view().name("ruleName/update"))
	.andExpect(model().attributeDoesNotExist("updateSuccess"))
	.andExpect(model().attributeDoesNotExist("updateError"))
	.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la suppression ruleName")
    public void deleteRuleNamePageTest() throws Exception {
	//WHEN
	when(ruleNameService.deleteRuleName(50)).thenReturn(true);
	mockMvc.perform(get("/ruleName/delete/50"))
	//THEN
		.andExpect(view().name("ruleName/list"))
		.andExpect(model().attributeExists("deleteSuccess"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de la suppression ruleName")
    public void deleteErrorRuleNamePageTest() throws Exception {
	//WHEN
	when(ruleNameService.deleteRuleName(50)).thenReturn(false);
	mockMvc.perform(get("/ruleName/delete/50"))
	//THEN
		.andExpect(view().name("ruleName/list"))
		.andExpect(model().attributeExists("deleteError"))
		.andExpect(status().isOk());
    }
}

