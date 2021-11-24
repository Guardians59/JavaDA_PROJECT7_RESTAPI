package com.nnk.springboot.repositories;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Rating;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RatingRepositoryTests {
    
    @Autowired
    RatingRepository ratingRepository;
    
    @Test
    @Order(1)
    public void getAllRatingTest() {
	//WHEN
	List<Rating> listResult = ratingRepository.findAll();
	//THEN
	assertTrue(listResult.size() > 0);
    }
    
    @Test
    @Order(2)
    public void saveRatingTest() {
	//GIVEN
	Rating rating = new Rating();
	rating.setMoodysRating("Test Moodys Rating");
	rating.setSandPRating("Test Sand Rating");
	rating.setFitchRating("Test Fitch Rating");
	rating.setOrderNumber(1);
	List<Rating> listResult = ratingRepository.findAll();
	int numberOfRating = listResult.size();
	//WHEN
	ratingRepository.save(rating);
	listResult = ratingRepository.findAll();
	int numberOfRatingAfterSave = listResult.size();
	//THEN
	assertEquals(numberOfRatingAfterSave, numberOfRating + 1);
    }
    
    @Test
    @Order(3)
    public void updateRatingTest() {
	//GIVEN
	List<Rating> listResult = ratingRepository.findAll();
	int numberOfRating = listResult.size();
	int index = numberOfRating - 1;
	Rating ratingUpdate = new Rating();
	ratingUpdate = listResult.get(index);
	ratingUpdate.setOrderNumber(2);
	//WHEN
	ratingRepository.save(ratingUpdate);
	listResult = ratingRepository.findAll();
	int orderNumber = listResult.get(index).getOrderNumber();
	//THEN
	assertEquals(listResult.size(), numberOfRating);
	assertEquals(orderNumber, 2);
    }
    
    @Test
    @Order(4)
    public void deleteRatingTest() {
	//GIVEN
	Rating ratingDelete = new Rating();
	List<Rating> listResult = ratingRepository.findAll();
	int numberOfRating = listResult.size();
	int index = numberOfRating - 1;
	ratingDelete = listResult.get(index);
	//WHEN
	ratingRepository.delete(ratingDelete);
	listResult = ratingRepository.findAll();
	Optional<Rating> rating = ratingRepository.findById(ratingDelete.getId());
	//THEN
	assertEquals(listResult.size(), numberOfRating - 1);
	assertEquals(rating.isPresent(), false);
    }

}
