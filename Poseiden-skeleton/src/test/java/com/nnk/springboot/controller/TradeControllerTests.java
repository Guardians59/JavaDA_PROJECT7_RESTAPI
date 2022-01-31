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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ITradeService tradeService;

    @BeforeEach
    public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste trade")
    public void getTradeListPageTest() throws Exception {
	//GIVEN
	List<Trade> list = new ArrayList<>();
	Trade trade = new Trade();
	trade.setTradeId(20);
	trade.setAccount("Account test");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	list.add(trade);
	//WHEN
	when(tradeService.getAllTrade()).thenReturn(list);
	mockMvc.perform(get("/trade/list"))
	//THEN
	.andExpect(view().name("trade/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout trade")
    public void getTradeAddPageTest() throws Exception {
	//WHEN
	mockMvc.perform(get("/trade/add"))
	//THEN
	.andExpect(view().name("trade/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'ajout trade")
    public void addTradePageTest() throws Exception {
	//GIVEN
	Trade trade = new Trade();
	trade.setTradeId(21);
	trade.setAccount("Account test");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	//WHEN
	when(tradeService.addTrade(trade)).thenReturn(true);
	mockMvc.perform(post("/trade/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("trade", trade))
	//THEN
		.andExpect(view().name("trade/list"))
		.andExpect(model().attributeExists("success"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de l'ajout trade")
    public void addErrorTradePageTest() throws Exception {
	//GIVEN
	Trade trade = new Trade();
	trade.setTradeId(21);
	trade.setAccount("Account test");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	//WHEN
	when(tradeService.addTrade(trade)).thenReturn(false);
	mockMvc.perform(post("/trade/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("trade", trade))
	//THEN
		.andExpect(view().name("trade/add"))
		.andExpect(model().attributeExists("error"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de l'ajout trade")
    public void addBindingErrorTradePageTest() throws Exception {
	//GIVEN
	Trade trade = new Trade();
	trade.setTradeId(21);
	trade.setAccount("");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	//WHEN
	when(tradeService.addTrade(trade)).thenReturn(false);
	mockMvc.perform(post("/trade/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("trade", trade))
	//THEN	
		.andExpect(view().name("trade/add"))
		.andExpect(model().attributeDoesNotExist("success"))
		.andExpect(model().attributeDoesNotExist("error"))
		.andExpect(status().isOk());
    }
	
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour trade")
    public void getTradeEditPageTest() throws Exception {
	//GIVEN
	Trade trade = new Trade();
	trade.setTradeId(21);
	trade.setAccount("Account test");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	//WHEN
	when(tradeService.getTradeById(21)).thenReturn(trade);
	mockMvc.perform(get("/trade/edit/21"))
	//THEN
	.andExpect(view().name("trade/update"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la mis à jour trade")
    public void updateTradePageTest() throws Exception {
	//GIVEN
	Trade trade = new Trade();
	trade.setTradeId(21);
	trade.setAccount("Account test");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	//WHEN
	when(tradeService.updateTrade(21, trade)).thenReturn(true);
	mockMvc.perform(post("/trade/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("trade", trade))
	//THEN
	.andExpect(view().name("trade/list"))
	.andExpect(model().attributeExists("updateSuccess"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de la mis à jour trade")
    public void updateErrorTradePageTest() throws Exception {
	//GIVEN
	Trade oldTrade = new Trade();
	oldTrade.setTradeId(21);
	oldTrade.setAccount("Account");
	oldTrade.setType("Type");
	oldTrade.setBuyQuantity(2.0);
	Trade trade = new Trade();
	trade.setTradeId(21);
	trade.setAccount("Account test");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	//WHEN
	when(tradeService.getTradeById(21)).thenReturn(oldTrade);
	when(tradeService.updateTrade(21, trade)).thenReturn(false);
	mockMvc.perform(post("/trade/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("trade", trade))
	//THEN
	.andExpect(view().name("trade/update"))
	.andExpect(model().attributeExists("updateError"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de la mis à jour trade")
    public void updateBindingErrorTradePageTest() throws Exception {
	//GIVEN
	Trade oldTrade = new Trade();
	oldTrade.setTradeId(21);
	oldTrade.setAccount("Account");
	oldTrade.setType("Type");
	oldTrade.setBuyQuantity(2.0);
	Trade trade = new Trade();
	trade.setTradeId(21);
	trade.setAccount("");
	trade.setType("Type test");
	trade.setBuyQuantity(4.0);
	//WHEN
	when(tradeService.getTradeById(21)).thenReturn(oldTrade);
	when(tradeService.updateTrade(21, trade)).thenReturn(false);
	mockMvc.perform(post("/trade/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("trade", trade))
	//THEN
	.andExpect(view().name("trade/update"))
	.andExpect(model().attributeDoesNotExist("updateSuccess"))
	.andExpect(model().attributeDoesNotExist("updateError"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la suppression trade")
    public void deleteTradePageTest() throws Exception {
	//WHEN
	when(tradeService.deleteTrade(22)).thenReturn(true);
	mockMvc.perform(get("/trade/delete/22"))
	//THEN
		.andExpect(view().name("trade/list"))
		.andExpect(model().attributeExists("deleteSuccess"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur de la suppression trade")
    public void deleteErrorTradePageTest() throws Exception {
	//WHEN
	when(tradeService.deleteTrade(22)).thenReturn(false);
	mockMvc.perform(get("/trade/delete/22"))
	//THEN
		.andExpect(view().name("trade/list"))
		.andExpect(model().attributeExists("deleteError"))
		.andExpect(status().isOk());
    }

}
