package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.services.IBidListService;
import com.nnk.springboot.services.IOAuthService;

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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * La classe BidListController est le controller qui permet de gérer les URL
 * bidList, afin d'afficher la liste des Entités BidList, ajouter,modifier et
 * supprimer un BidList.
 * 
 * @author Dylan
 *
 */
@Controller
public class BidListController {

    @Autowired
    IBidListService bidListService;

    @Autowired
    IOAuthService oauthService;
    
    private static Logger logger = LogManager.getLogger(BidListController.class);

    /**
     * La méthode home permet d'afficher la liste des BidList enregistrés en base
     * de données.
     * @param principal les informations de l'utilisateur connécté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue bidList/list.
     */
    @GetMapping("/bidList/list")
    public String home(Principal principal, Model model) {
	/*
	 * On récupère la liste des BidList via le service bidListService, on
	 * indique le model bidList avec l'attribut correspondant qui va permettre
	 * à thymeleaf d'afficher celle-ci.
	 * On récupère l'username de l'utilisateur connecté avec l'objet LoggedUsername
	 * qui utilise le service oauthService.
	 * Nous renvoyons la vue bidList/list.
	 */
	List<BidList> bidList = new ArrayList<>();
	bidList = bidListService.getAllBidList();
	model.addAttribute("bidList", bidList);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	model.addAttribute("loggedusername", logged);
	logger.info("The user " + logged.getUsername() + " logs in to the endpoint '/bidList/list'");
	return "bidList/list";
    }
    
    /**
     * La méthode addBidForm permet d'afficher le formulaire d'ajout d'un BidList.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @param principal les informations de l'utilisateur connecté.
     * @return String la vue bidList/add.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Principal principal, Model model) {
	BidList bidList = new BidList();
	model.addAttribute("bidList", bidList);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	logger.info("The user " + logged.getUsername() + 
		" logs in to the add form from endpoint '/bidList/add'");
	return "bidList/add";
    }

    /**
     * La méthode validate permet de poster le formulaire d'ajout d'un BidList.
     * @param bid le model de l'entité BidList à sauvegarder en base de données.
     * @param result binding result afin de vérifier la conformité des informations
     * du BidList.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue bidList/list si l'ajout est un succès, bidList/add si
     * une erreur est rencontrée.
     */
    @PostMapping(value = "/bidList/validate")
    public String validate(@ModelAttribute("bidList") @Valid BidList bid, BindingResult result,
	    Principal principal, Model model) {
	// TODO: check data valid and save to db, after saving return bid list
	boolean resultAdd;
	resultAdd = bidListService.addBidList(bid);
	
	/*
	 * On récupère l'username de l'utilisateur connecté avec l'objet
	 * LoggedUsername qui utilise le service oauthService.
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page bidList/add avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service d'ajout d'un
	 * BidList renvoit true pour confirmer l'ajout de celui-ci, nous renvoyons
	 * la page bidList/list avec un message de succès pour indiquer que la sauvegarde
	 * c'est bien exécutée.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer l'ajout du BidList alors nous renvoyons la page bidList/add
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
		    " has encounter an error in the endpoint '/bidList/validate'");
	    return "bidList/add";
	} else if (resultAdd == true) {
	    List<BidList> bidList = new ArrayList<>();
	    bidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", bidList);
	    model.addAttribute("success", "Successful bid addition");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " just added a new bidList from the endpoint post '/bidList/validate'");
	    return "bidList/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/bidList/validate'");
	    return "bidList/add";
	}
    }

    /**
     * La méthode showUpdateForm permet d'afficher le formulaire de mis à jour
     * d'un BidList.
     * @param id l'id du BidList que l'on souhaite modifier.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue bidList/update.
     */
    @GetMapping("/bidList/edit/{bidListId}")
    public String showUpdateForm(@PathVariable("bidListId") int id, Principal principal, Model model) {
	// TODO: get Bid by Id and to model then show to the form
	BidList bidList = bidListService.getBidById(id);
	model.addAttribute("bidList", bidList);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	logger.info("The user " + logged.getUsername() + 
		" logs in to the update form from endpoint '/bidList/edit/" + id + "'");
	return "bidList/update";
    }

    /**
     * La méthode updateBid permet de poster la mis à jour d'un BidList.
     * @param id l'id du BidList à modifier.
     * @param bidList le model de l'entité BidList.
     * @param result binding result afin de vérifier la conformité des informations
     * du BidList.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue bidList/list si la mis à jour est validée, bidList/update
     * si une erreur est rencontrée.
     */
    @PostMapping("/bidList/update/{bidListId}")
    public String updateBid(@PathVariable("bidListId") int id, @ModelAttribute("bidList") @Valid BidList bidList,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update Bid and return
	boolean resultUpdate;
	resultUpdate = bidListService.updateBidList(id, bidList);

	/*
	 * On récupère l'username de l'utilisateur connecté avec l'objet
	 * LoggedUsername qui utilise le service oauthService.
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page bidList/update avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service de mis à jour d'un
	 * BidList renvoit true pour confirmer la modification de celui-ci, nous renvoyons
	 * la page bidList/list avec un message de succès pour indiquer que la mis à jour
	 * c'est bien exécutée.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer la mis à jour du BidList alors nous renvoyons la page
	 * bidList/update avec un message d'erreur.
	 */
	LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	if (result.hasErrors()) {
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/bidList/update/" + id + "'");
	    return "bidList/update";
	} else if (resultUpdate == true) {
	    List<BidList> listBidList = new ArrayList<>();
	    listBidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", listBidList);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " just updated bidList from endpoint post '/bidList/update/" + id + "'");
	    return "bidList/list";
	} else {
	    BidList bidListModel = bidListService.getBidById(id);
	    model.addAttribute("bidList", bidListModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/bidList/update/" + id + "'");
	    return "bidList/update";
	}

    }

    /**
     * La méthode deleteBid permet de supprimer un BidList depuis la liste.
     * @param id l'id du BidList à supprimer.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue bidList/list.
     */
    @GetMapping("/bidList/delete/{bidListId}")
    public String deleteBid(@PathVariable("bidListId") Integer id, Principal principal, Model model) {
	// TODO: Find Bid by Id and delete the bid, return to Bid list
	boolean resultDelete;
	resultDelete = bidListService.deleteBidList(id);

	/*
	 * On récupère l'username de l'utilisateur connecté avec l'objet
	 * LoggedUsername qui utilise le service oauthService.
	 * Si le service deleteBidList nous renvoit true, alors nous affichons la
	 * liste mis à jour avec un message de succès pour indiquer la validation
	 * de la suppression du BidList.
	 * Si le service deleteBidList nous renvoit false, alors nous affichons un
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
	    List<BidList> bidList = new ArrayList<>();
	    bidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", bidList);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " has deleted a bidList from the endpoint '/bidList/delete/" + id + "'");
	    return "bidList/list";
	} else {
	    List<BidList> bidList = new ArrayList<>();
	    bidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", bidList);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    model.addAttribute("loggedusername", logged);
	    logger.info("The user " + logged.getUsername() + 
		    " has encounter an error in the endpoint '/bidList/delete/" + id + "'");
	    return "bidList/list";
	}
    }
}
