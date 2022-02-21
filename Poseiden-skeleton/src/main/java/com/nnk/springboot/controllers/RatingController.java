package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IOAuthService;
import com.nnk.springboot.services.IRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class RatingController {

    @Autowired
    IRatingService ratingService;

    @Autowired
    IOAuthService oauthService;

    @RequestMapping("/rating/list")
    public String home(Principal principal, Model model) {
	// TODO: find all Rating, add to model
	List<Rating> ratingList = new ArrayList<>();
	ratingList = ratingService.getAllRating();
	model.addAttribute("rating", ratingList);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	model.addAttribute("loggedusername", logged);
	return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
	Rating rating = new Rating();
	model.addAttribute("rating", rating);
	return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, Principal principal,
	    Model model) {
	// TODO: check data valid and save to db, after saving return Rating list
	boolean resultAdd;
	resultAdd = ratingService.addRating(rating);

	if (result.hasErrors()) {
	    return "rating/add";
	} else if (resultAdd == true) {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("success", "Successful rating addition");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "rating/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "rating/add";
	}
    }

    @GetMapping("/rating/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
	// TODO: get Rating by Id and to model then show to the form
	Rating rating = new Rating();
	rating = ratingService.getRatingById(id);
	model.addAttribute("rating", rating);
	return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") int id, @ModelAttribute("rating") @Valid Rating rating,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update Rating and
	// return Rating list
	boolean resultUpdate;
	resultUpdate = ratingService.updateRating(id, rating);

	if (result.hasErrors()) {
	    return "rating/update";
	} else if (resultUpdate == true) {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "rating/list";
	} else {
	    Rating ratingModel = ratingService.getRatingById(id);
	    model.addAttribute("rating", ratingModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "rating/update";
	}
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") int id, Principal principal, Model model) {
	// TODO: Find Rating by Id and delete the Rating, return to Rating list
	boolean resultDelete;
	resultDelete = ratingService.deleteRating(id);

	if (resultDelete == true) {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	} else {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	}
	return "rating/list";
    }
}
