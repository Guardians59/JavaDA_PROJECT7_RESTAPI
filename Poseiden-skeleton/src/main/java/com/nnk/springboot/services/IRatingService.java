package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

public interface IRatingService {
    
    public List<Rating> getAllRating();
    
    public boolean addRating(Rating newRating);
    
    public boolean updateRating(int id, Rating rating);
    
    public boolean deleteRating(int id);
    
    public Rating getRatingById(int id);

}
