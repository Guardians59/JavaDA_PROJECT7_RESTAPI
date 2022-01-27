package com.nnk.springboot.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IBidListService bidListService;

    @BeforeEach
    public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste bidList")
    public void getBidListListPageTest() throws Exception {
	// GIVEN
	List<BidList> list = new ArrayList<>();
	BidList bidListOne = new BidList();
	bidListOne.setBidListId(4);
	bidListOne.setAccount("get list test");
	bidListOne.setType("get list type test");
	bidListOne.setBidQuantity(4.0);
	list.add(bidListOne);
	// WHEN
	when(bidListService.getAllBidList()).thenReturn(list);
	mockMvc.perform(get("/bidList/list"))
		// THEN
		.andExpect(view().name("bidList/list")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout bidList")
    public void getBidListAddPageTest() throws Exception {
	// WHEN
	mockMvc.perform(get("/bidList/add"))
		// THEN
		.andExpect(view().name("bidList/add")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'ajout d'un bidList")
    public void addBidListTest() throws Exception {
	// GIVEN
	BidList newBidList = new BidList();
	newBidList.setAccount("Integration Test");
	newBidList.setType("Integration Type");
	newBidList.setBidQuantity(15.0);
	// WHEN
	when(bidListService.addBidList(newBidList)).thenReturn(true);
	mockMvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("bidList", newBidList))
		// THEN
		.andExpect(view().name("bidList/list")).andExpect(model().attributeExists("success"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors l'ajout d'un bidList")
    public void addErrorBidListTest() throws Exception {
	// GIVEN
	BidList newBidList = new BidList();
	newBidList.setAccount("Integration Test");
	newBidList.setType("Integration Type");
	newBidList.setBidQuantity(12.0);
	// WHEN
	when(bidListService.addBidList(newBidList)).thenReturn(false);
	mockMvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("bidList", newBidList))
		// THEN
		.andExpect(view().name("bidList/add")).andExpect(model().attributeExists("error"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors l'ajout d'un bidList")
    public void addBindingErrorBidListTest() throws Exception {
	// GIVEN
	BidList newBidList = new BidList();
	newBidList.setAccount("Integration Test");
	newBidList.setType("");
	newBidList.setBidQuantity(12.0);
	// WHEN
	when(bidListService.addBidList(newBidList)).thenReturn(false);
	mockMvc.perform(post("/bidList/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("bidList", newBidList))
		// THEN
		.andExpect(view().name("bidList/add")).andExpect(model().attributeDoesNotExist("success"))
		.andExpect(model().attributeDoesNotExist("error")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour bidList")
    public void getBidListEditPageTest() throws Exception {
	// GIVEN
	BidList newBidList = new BidList();
	newBidList.setBidListId(3);
	newBidList.setAccount("Test get");
	newBidList.setType("Test get type");
	newBidList.setBidQuantity(2.0);
	// WHEN
	when(bidListService.getBidById(3)).thenReturn(newBidList);
	mockMvc.perform(get("/bidList/edit/3"))
		// THEN
		.andExpect(view().name("bidList/update")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la mis à jour d'un bidList")
    public void updateBidListTest() throws Exception {
	// GIVEN
	BidList newBidList = new BidList();
	newBidList.setAccount("Integration Test");
	newBidList.setType("Integration Type");
	newBidList.setBidQuantity(17.0);
	// WHEN
	when(bidListService.updateBidList(26, newBidList)).thenReturn(true);
	mockMvc.perform(post("/bidList/update/26").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("bidList", newBidList))
		// THEN
		.andExpect(view().name("bidList/list")).andExpect(model().attributeExists("updateSuccess"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de la mis à jour d'un bidList")
    public void updateErrorBidListTest() throws Exception {
	// GIVEN
	BidList oldBidList = new BidList();
	oldBidList.setBidListId(30);
	oldBidList.setAccount("Old Account");
	oldBidList.setType("Old Type");
	oldBidList.setBidQuantity(1.0);
	BidList newBidList = new BidList();
	newBidList.setAccount("Integration Test");
	newBidList.setType("Error");
	newBidList.setBidQuantity(2.0);
	// WHEN
	when(bidListService.updateBidList(30, newBidList)).thenReturn(false);
	when(bidListService.getBidById(30)).thenReturn(oldBidList);
	mockMvc.perform(post("/bidList/update/30").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("bidList", newBidList))
		// THEN
		.andExpect(view().name("bidList/update")).andExpect(model().attributeExists("updateError"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur bindingResult lors de la mis à jour d'un bidList")
    public void updateBindingErrorBidListTest() throws Exception {
	// GIVEN
	BidList newBidList = new BidList();
	newBidList.setAccount("Integration Test");
	newBidList.setType("");
	newBidList.setBidQuantity(25.0);
	// WHEN
	when(bidListService.updateBidList(30, newBidList)).thenReturn(false);
	mockMvc.perform(post("/bidList/update/30").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("bidList", newBidList))
		// THEN
		.andExpect(view().name("bidList/update")).andExpect(model().attributeDoesNotExist("updateSuccess"))
		.andExpect(model().attributeDoesNotExist("updateError")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de suppression bidList")
    public void getBidListDeletePageTest() throws Exception {
	// WHEN
	when(bidListService.deleteBidList(26)).thenReturn(true);
	mockMvc.perform(get("/bidList/delete/26"))
		// THEN
		.andExpect(view().name("bidList/list")).andExpect(model().attributeExists("deleteSuccess"))
		.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue avec l'erreur de suppression bidList")
    public void getErrorBidListDeletePageTest() throws Exception {
	// WHEN
	when(bidListService.deleteBidList(1)).thenReturn(false);
	mockMvc.perform(get("/bidList/delete/1"))
		// THEN
		.andExpect(view().name("bidList/list")).andExpect(model().attributeExists("deleteError"))
		.andExpect(status().isOk());
    }

}
