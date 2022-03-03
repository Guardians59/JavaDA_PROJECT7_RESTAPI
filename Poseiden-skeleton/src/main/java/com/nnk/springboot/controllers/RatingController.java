package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IOAuthService;
import com.nnk.springboot.services.IRatingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * La classe RatingController est le controller qui permet de gérer les URL rating,
 * afin d'afficher la liste des entités Rating, ajouter, modifier et supprimer
 * un Rating.
 * 
 * @author Dylan
 *
 */
@Controller
public class RatingController {

    @Autowired
    IRatingService ratingService;

    @Autowired
    IOAuthService oauthService;
    
    private static Logger logger = LogManager.getLogger(RatingController.class);

    /**
     * La méthode home permet d'afficher la liste des Rating enregistrés en base
     * de données.
     * @param principal les informations de l'utilisateur connécté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue rating/list.
     */
    @RequestMapping("/rating/list")
    public String home(Principal principal, Model model) {
	/*
	 * On récupère la liste des Rating via le service ratingService, on
	 * indique le model rating avec l'attribut correspondant qui va permettre
	 * à thymeleaf d'afficher celle-ci.
	 * On récupère l'username de l'utilisateur connecté avec l'objet LoggedUsername
	 * qui utilise le service oauthService.
	 * Nous renvoyons la vue rating/list.
	 */
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
	logger.info("The user " + logged.getUsername() + 
		" logs in to the endpoint '/rating/list'");
	return "rating/list";
    }

    /**
     * La méthode addRatingForm permet d'afficher le formulaire d'ajout d'un Rating.ù
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue rating/add.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Principal principal, Model model) {
	Rating rating = new Rating();
	model.addAttribute("rating", rating);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	logger.info("The user " + logged.getUsername() + 
		" logs in to the add form from endpoint '/rating/add'");
	return "rating/add";
    }

    /**
     * La méthode validate permet de poster le formulaire d'ajout d'un Rating.
     * @param rating le model de l'entité Rating à sauvegarder en base de données.
     * @param result binding result afin de vérifier la conformité des informations
     * du Rating.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue rating/list si l'ajout est un succès, rating/add si
     * une erreur est rencontrée.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result,
	    Principal principal, Model model) {
	// TODO: check data valid and save to db, after saving return Rating list
	boolean resultAdd;
	resultAdd = ratingService.addRating(rating);

	/*
	 * On récupère l'username de l'utilisateur connecté avec l'objet
	 * LoggedUsername qui utilise le service oauthService.
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page rating/add avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service d'ajout d'un
	 * Rating renvoit true pour confirmer l'ajout de celui-ci, nous renvoyons
	 * la page rating/list avec un message de succès pour indiquer que la sauvegarde
	 * c'est bien exécutée.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer l'ajout du Rating alors nous renvoyons la page rating/add
	 * avec un message d'erreur.
	 */
	LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	if (result.hasErrors()) {
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/rating/validate'");
	    return "rating/add";
	} else if (resultAdd == true) {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("success", "Successful rating addition");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " just added a new rating from the endpoint post '/rating/validate'");
	    return "rating/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/rating/validate'");
	    return "rating/add";
	}
    }

    /**
     * La méthode showUpdateForm permet d'afficher le formulaire de mis à jour
     * d'un Rating.
     * @param id l'id du Rating que l'on souhaite modifier.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue rating/update.
     */
    @GetMapping("/rating/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Principal principal, Model model) {
	// TODO: get Rating by Id and to model then show to the form
	Rating rating = new Rating();
	rating = ratingService.getRatingById(id);
	model.addAttribute("rating", rating);
	LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    logger.info("The user " + logged.getUsername() + 
			" logs in to the update form from endpoint '/rating/edit/" + id + "'");
	return "rating/update";
    }

    /**
     * La méthode updateRating permet de poster la mis à jour d'un Rating.
     * @param id l'id du Rating que l'on souhaite modifier.
     * @param rating le model de l'entité Rating.
     * @param result binding result afin de vérifier la conformité des informations
     * du Rating.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue rating/list si la mis à jour est validée, rating/update
     * si une erreur est rencontrée.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") int id, @ModelAttribute("rating") @Valid Rating rating,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update Rating and
	boolean resultUpdate;
	resultUpdate = ratingService.updateRating(id, rating);
	
	/*
	 * On récupère l'username de l'utilisateur connecté avec l'objet
	 * LoggedUsername qui utilise le service oauthService.
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page rating/update avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service de mis à jour d'un
	 * Rating renvoit true pour confirmer la modification de celui-ci, nous renvoyons
	 * la page rating/list avec un message de succès pour indiquer que la mis à jour
	 * c'est bien exécutée.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer la mis à jour du Rating alors nous renvoyons la page
	 * rating/update avec un message d'erreur.
	 */
	LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	if (result.hasErrors()) {
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/rating/update/" + id + "'");
	    return "rating/update";
	} else if (resultUpdate == true) {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " just updated rating from endpoint post '/rating/update/" + id + "'");
	    return "rating/list";
	} else {
	    Rating ratingModel = ratingService.getRatingById(id);
	    model.addAttribute("rating", ratingModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/rating/update/" + id + "'");
	    return "rating/update";
	}
    }

    /**
     * La méthode deleteRating permet de supprimer un Rating depuis la liste.
     * @param id l'id du Rating que l'on souhaite supprimer.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue rating/list.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") int id, Principal principal, Model model) {
	// TODO: Find Rating by Id and delete the Rating, return to Rating list
	boolean resultDelete;
	resultDelete = ratingService.deleteRating(id);

	/*
	 * On récupère l'username de l'utilisateur connecté avec l'objet
	 * LoggedUsername qui utilise le service oauthService.
	 * Si le service deleteRating nous renvoit true, alors nous affichons la
	 * liste mis à jour avec un message de succès pour indiquer la validation
	 * de la suppression du Rating.
	 * Si le service deleteRating nous renvoit false, alors nous affichons un
	 * message d'erreur au dessus de la liste afin d'indiquer que la suppression
	 * n'est pas validée.
	 */
	LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	if (resultDelete == true) {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " has deleted a rating from the endpoint '/rating/delete/" + id + "'");
	} else {
	    List<Rating> ratingList = new ArrayList<>();
	    ratingList = ratingService.getAllRating();
	    model.addAttribute("rating", ratingList);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/rating/delete/" + id + "'");
	}
	return "rating/list";
    }
}
