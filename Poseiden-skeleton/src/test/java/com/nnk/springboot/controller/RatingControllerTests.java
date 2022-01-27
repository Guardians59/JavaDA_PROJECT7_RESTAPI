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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IRatingService ratingService;

    @BeforeEach
    public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de la liste rating")
    public void getRatingListPageTest() throws Exception {
	//GIVEN
	List<Rating> list = new ArrayList<>();
	Rating rating = new Rating();
	rating.setId(20);
	rating.setOrderNumber(2);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	list.add(rating);
	//WHEN
	when(ratingService.getAllRating()).thenReturn(list);
	mockMvc.perform(get("/rating/list"))
	//THEN
	.andExpect(view().name("rating/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue d'ajout rating")
    public void getRatingAddPageTest() throws Exception {
	//WHEN
	mockMvc.perform(get("/rating/add"))
	//THEN
	.andExpect(view().name("rating/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'ajout rating")
    public void addRatingTest() throws Exception {
	//GIVEN
	Rating rating = new Rating();
	rating.setId(20);
	rating.setOrderNumber(2);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	//WHEN
	when(ratingService.addRating(rating)).thenReturn(true);
	mockMvc.perform(post("/rating/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("rating", rating))
	//THEN
		.andExpect(view().name("rating/list"))
		.andExpect(model().attributeExists("success"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de l'ajout rating")
    public void addErrorRatingTest() throws Exception {
	//GIVEN
	Rating rating = new Rating();
	rating.setId(20);
	rating.setOrderNumber(2);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	//WHEN
	when(ratingService.addRating(rating)).thenReturn(false);
	mockMvc.perform(post("/rating/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("rating", rating))
	//THEN
		.andExpect(view().name("rating/add"))
		.andExpect(model().attributeExists("error"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de l'ajout rating")
    public void addErrorBindingRatingTest() throws Exception {
	//GIVEN
	Rating rating = new Rating();
	rating.setId(20);
	rating.setOrderNumber(0);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	//WHEN
	when(ratingService.addRating(rating)).thenReturn(false);
	mockMvc.perform(post("/rating/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("rating", rating))
	//THEN
		.andExpect(view().name("rating/add"))
		.andExpect(model().attributeDoesNotExist("success"))
		.andExpect(model().attributeDoesNotExist("error"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la vue de mis à jour rating")
    public void getRatingEditPageTest() throws Exception {
	//GIVEN
	Rating rating = new Rating();
	rating.setId(21);
	rating.setOrderNumber(3);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	//WHEN
	when(ratingService.getRatingById(21)).thenReturn(rating);
	mockMvc.perform(get("/rating/edit/21"))
	//THEN
	.andExpect(view().name("rating/update"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de la mis à jour rating")
    public void updateRatingPageTest() throws Exception {
	//GIVEN
	Rating rating = new Rating();
	rating.setId(21);
	rating.setOrderNumber(3);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	//WHEN
	when(ratingService.updateRating(21, rating)).thenReturn(true);
	mockMvc.perform(post("/rating/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
	       .flashAttr("rating", rating))
		
	//THEN
	.andExpect(view().name("rating/list"))
	.andExpect(model().attributeExists("updateSuccess"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur lors de la mis à jour rating")
    public void updateErrorRatingPageTest() throws Exception {
	//GIVEN
	Rating oldRating = new Rating();
	oldRating.setId(21);
	oldRating.setOrderNumber(4);
	oldRating.setMoodysRating("Moodys");
	oldRating.setSandPRating("SandPRating");
	oldRating.setFitchRating("FitchRating");
	Rating rating = new Rating();
	rating.setId(21);
	rating.setOrderNumber(3);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	//WHEN
	when(ratingService.getRatingById(21)).thenReturn(oldRating);
	when(ratingService.updateRating(21, rating)).thenReturn(false);
	mockMvc.perform(post("/rating/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
	       .flashAttr("rating", rating))
		
	//THEN
	.andExpect(view().name("rating/update"))
	.andExpect(model().attributeExists("updateError"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur binding lors de la mis à jour rating")
    public void updateErrorBindingRatingPageTest() throws Exception {
	//GIVEN
	Rating oldRating = new Rating();
	oldRating.setId(21);
	oldRating.setOrderNumber(4);
	oldRating.setMoodysRating("Moodys");
	oldRating.setSandPRating("SandPRating");
	oldRating.setFitchRating("FitchRating");
	Rating rating = new Rating();
	rating.setId(21);
	rating.setOrderNumber(0);
	rating.setMoodysRating("Moodys test");
	rating.setSandPRating("SandPRating test");
	rating.setFitchRating("FitchRating test");
	//WHEN
	when(ratingService.getRatingById(21)).thenReturn(oldRating);
	when(ratingService.updateRating(21, rating)).thenReturn(false);
	mockMvc.perform(post("/rating/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
	       .flashAttr("rating", rating))
		
	//THEN
	.andExpect(view().name("rating/update"))
	.andExpect(model().attributeDoesNotExist("updateSuccess"))
	.andExpect(model().attributeDoesNotExist("updateError"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de suppression rating")
    public void getRatingDeletePageTest() throws Exception {
	//WHEN
	when(ratingService.deleteRating(22)).thenReturn(true);
	mockMvc.perform(get("/rating/delete/22"))
	//THEN
		.andExpect(view().name("rating/list"))
		.andExpect(model().attributeExists("deleteSuccess"));
    }
    
    @Test
    @WithUserDetails("user test")
    @DisplayName("Test de l'erreur de suppression rating")
    public void getErrorRatingDeletePageTest() throws Exception {
	//WHEN
	when(ratingService.deleteRating(22)).thenReturn(false);
	mockMvc.perform(get("/rating/delete/22"))
	//THEN
		.andExpect(view().name("rating/list"))
		.andExpect(model().attributeExists("deleteError"));
    }

}
