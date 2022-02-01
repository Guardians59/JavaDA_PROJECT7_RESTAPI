package com.nnk.springboot.services;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RatingServiceTests {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    IRatingService ratingService;

    @Test
    @Order(1)
    public void getAllRatingServiceTest() {
	// WHEN
	List<Rating> listRating = ratingService.getAllRating();
	// THEN
	assertEquals(listRating.isEmpty(), false);
    }

    @Test
    @Order(2)
    public void addNewRatingServiceTest() {
	// GIVEN
	boolean result;
	List<Rating> listRating = ratingRepository.findAll();
	int sizeBeforeSave = listRating.size();
	Rating rating = new Rating();
	rating.setMoodysRating("Test Moodys");
	rating.setSandPRating("Test Sand");
	rating.setFitchRating("Test Fitch");
	rating.setOrderNumber(4);
	// WHEN
	result = ratingService.addRating(rating);
	listRating = ratingRepository.findAll();
	// THEN
	assertEquals(result, true);
	assertEquals(listRating.size(), sizeBeforeSave + 1);
    }

    @Test
    @Order(3)
    public void addNewRatingServiceErrorTest() {
	// GIVEN
	boolean result;
	List<Rating> listRating = ratingRepository.findAll();
	int sizeBeforeSave = listRating.size();
	Rating rating = new Rating();
	rating.setMoodysRating("Test Moodys");
	rating.setSandPRating("Test Sand");
	rating.setFitchRating("Test Fitch");
	rating.setOrderNumber(0);
	// WHEN
	result = ratingService.addRating(rating);
	listRating = ratingRepository.findAll();
	// THEN
	assertEquals(result, false);
	assertEquals(listRating.size(), sizeBeforeSave);
    }

    @Test
    @Order(4)
    public void updateRatingServiceTest() {
	// GIVEN
	boolean result;
	List<Rating> listRating = ratingRepository.findAll();
	int numberOfRating = listRating.size();
	int index = numberOfRating - 1;
	Rating ratingUpdate = new Rating();
	ratingUpdate = listRating.get(index);
	int id = ratingUpdate.getId();
	ratingUpdate.setOrderNumber(2);
	// WHEN
	result = ratingService.updateRating(id, ratingUpdate);
	Optional<Rating> rating = ratingRepository.findById(id);
	Rating ratingAfterUpdate = rating.get();
	int orderNumber = ratingAfterUpdate.getOrderNumber();
	// THEN
	assertEquals(result, true);
	assertEquals(orderNumber, 2);
    }

    @Test
    @Order(5)
    public void updateRatingServiceErrorTest() {
	// GIVEN
	boolean result;
	List<Rating> listRating = ratingRepository.findAll();
	int numberOfRating = listRating.size();
	int index = numberOfRating - 1;
	Rating ratingUpdate = new Rating();
	ratingUpdate = listRating.get(index);
	int id = ratingUpdate.getId();
	ratingUpdate.setOrderNumber(0);
	// WHEN
	result = ratingService.updateRating(id, ratingUpdate);
	Optional<Rating> rating = ratingRepository.findById(id);
	Rating ratingAfterUpdate = rating.get();
	int orderNumber = ratingAfterUpdate.getOrderNumber();
	// THEN
	assertEquals(result, false);
	assertEquals(orderNumber, 2);
    }
    
    @Test
    @Order(6)
    public void updateFalseIdRatingServiceTest() {
	//GIVEN
	boolean result;
	int falseId = 2;
	Rating rating = new Rating();
	rating.setOrderNumber(2);
	rating.setFitchRating("error test");
	rating.setMoodysRating("error test");
	rating.setSandPRating("error test");
	//WHEN
	result = ratingService.updateRating(falseId, rating);
	//THEN
	assertEquals(result, false);
    }

    @Test
    @Order(7)
    public void deleteRatingServiceTest() {
	// GIVEN
	boolean result;
	List<Rating> listRating = ratingRepository.findAll();
	int numberOfRating = listRating.size();
	int index = numberOfRating - 1;
	Rating ratingDelete = new Rating();
	ratingDelete = listRating.get(index);
	int id = ratingDelete.getId();
	//WHEN
	result = ratingService.deleteRating(id);
	Optional<Rating> rating = ratingRepository.findById(id);
	//THEN
	assertEquals(result, true);
	assertEquals(rating.isPresent(), false);
    }
    
    @Test
    @Order(8)
    public void deleteRatingServiceErrorTest() {
	// GIVEN
	boolean result;
	int falseId = 2;
	//WHEN
	result = ratingService.deleteRating(falseId);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    @Order(9)
    public void getRatingByIdServiceTest() {
	//GIVEN
	Rating rating = new Rating();
	//WHEN
	rating = ratingService.getRatingById(1);
	//THEN
	assertEquals(rating.getMoodysRating(), "Moodys Rating");
	assertEquals(rating.getFitchRating(), "Fitch Rating");
    }
    
    @Test
    @Order(10)
    public void getRatingByIdServiceErrorTest() {
	//GIVEN
	Rating rating = new Rating();
	//WHEN
	rating = ratingService.getRatingById(2);
	//THEN
	assertEquals(rating.getMoodysRating(), null);
	assertFalse(rating.getId() > 0);
    }

}
