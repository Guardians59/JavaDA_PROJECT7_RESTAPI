package com.nnk.springboot.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.IRatingService;

@Service
public class RatingServiceImpl implements IRatingService{
    
    @Autowired
    RatingRepository ratingRepository;
    
    private static Logger logger = LogManager.getLogger(RatingServiceImpl.class);

    @Override
    public List<Rating> getAllRating() {
	List<Rating> ratingList = new ArrayList<>();
	ratingList = ratingRepository.findAll();
	if (ratingList.isEmpty()) {
	    logger.info("The list of rating is empty");
	} else {
	    logger.info("List of rating successfully recovered");
	}
	return ratingList;
    }

    @Override
    @Transactional
    public boolean addRating(Rating newRating) {
	boolean result = false;
	if(!newRating.getMoodysRating().isEmpty() && !newRating.getSandPRating().isEmpty()
		&& !newRating.getFitchRating().isEmpty() && newRating.getOrderNumber() > 0) {
	    
	    Rating rating = new Rating();
	    rating.setMoodysRating(newRating.getMoodysRating());
	    rating.setSandPRating(newRating.getSandPRating());
	    rating.setFitchRating(newRating.getFitchRating());
	    rating.setOrderNumber(newRating.getOrderNumber());
	    ratingRepository.save(rating);
	    result = true;
	    logger.info("The new rating is registered successfully");
	} else {
	    logger.error("A data in the form is missing");
	}
	
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateRating(int id, Rating rating) {
	boolean result = false;
	Rating ratingUpdate = new Rating();
	Optional<Rating> searchRating;
	searchRating = ratingRepository.findById(id);
	
	if (searchRating.isPresent()) {
	    if (!rating.getMoodysRating().isEmpty() && !rating.getSandPRating().isEmpty()
		    && !rating.getFitchRating().isEmpty() && rating.getOrderNumber() > 0) {
		ratingUpdate = searchRating.get();
		ratingUpdate.setMoodysRating(rating.getMoodysRating());
		ratingUpdate.setSandPRating(rating.getSandPRating());
		ratingUpdate.setFitchRating(rating.getFitchRating());
		ratingUpdate.setOrderNumber(rating.getOrderNumber());
		ratingRepository.save(ratingUpdate);
		result = true;
		logger.info("Updated rating with id number " + id + " successfully");
	    } else {
		logger.error("A data in the form is missing");
	    } 

	} else {
	    logger.error("No rating found with id number " + id);
	    }
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteRating(int id) {
	boolean result = false;
	Optional<Rating> ratingDelete;
	ratingDelete = ratingRepository.findById(id);
	
	if (ratingDelete.isPresent()) {
	    ratingRepository.deleteById(id);
	    result = true;
	    logger.info("The rating with id number " + id + " has been deleted correctly");
	} else {
	    logger.error("No rating found with id number " + id);
	}
	return result;
    }

    @Override
    public Rating getRatingById(int id) {
	Optional<Rating> searchRating;
	searchRating = ratingRepository.findById(id);
	Rating rating = new Rating();
	
	if (searchRating.isPresent()) {
	    rating = searchRating.get();
	    logger.info("The rating with id number " + id + " successfully recovered");
	} else {
	    logger.error("No rating found with id number " + id);
	}
	return rating;
    }

}
