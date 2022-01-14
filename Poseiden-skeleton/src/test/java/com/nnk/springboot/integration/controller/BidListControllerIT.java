package com.nnk.springboot.integration.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class BidListControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    BidListRepository bidListRepository;
    
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
    @WithUserDetails("user test")
    @DisplayName("Test de l'ajout d'un bidList")
    @Order(1)
    public void addBidListTest() throws Exception {
	BidList newBidList = new BidList();
	newBidList.setAccount("Integration Test");
	newBidList.setType("Integration Type");
	newBidList.setBidQuantity(15.0);
	mockMvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("bidList", newBidList))
		.andExpect(view().name("bidList/list"))
		.andExpect(model().attributeExists("success"))
		.andExpect(status().isOk()); 
	
	/*List<BidList> listResult = bidListRepository.findAll();
	int numberOfBid = listResult.size();
	int index = numberOfBid - 1;
	BidList bidListDelete = new BidList();
	bidListDelete = listResult.get(index);
	int id = bidListDelete.getBidListId();
	bidListRepository.deleteById(id);*/
	
    }

}
