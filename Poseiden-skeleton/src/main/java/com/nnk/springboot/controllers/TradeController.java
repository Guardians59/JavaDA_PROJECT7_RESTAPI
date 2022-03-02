package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.IOAuthService;
import com.nnk.springboot.services.ITradeService;

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
 * La classe TradeController est le controller qui permet de gérer les URL trade,
 * afin d'afficher la liste, ajouter, modifier et supprimer un Trade.
 * 
 * @author Dylan
 *
 */
@Controller
public class TradeController {

    @Autowired
    ITradeService tradeService;

    @Autowired
    IOAuthService oauthService;

    /**
     * La méthode home permet d'afficher la liste des Trade enregistrés en base
     * de données.
     * @param principal les informations de l'utilisateur connécté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue trade/list.
     */
    @RequestMapping("/trade/list")
    public String home(Principal principal, Model model) {
	/*
	 * On récupère la liste des Trade via le service tradeService, on
	 * indique le model trade avec l'attribut correspondant qui va permettre
	 * à thymeleaf d'afficher celle-ci.
	 * On récupère l'username de l'utilisateur connecté avec l'objet LoggedUsername
	 * qui utilise le service oauthService.
	 * Nous renvoyons la vue trade/list.
	 */
	List<Trade> listTrade = new ArrayList<>();
	listTrade = tradeService.getAllTrade();
	model.addAttribute("trade", listTrade);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	model.addAttribute("loggedusername", logged);
	return "trade/list";
    }

    /**
     * La méthode addTradeForm permet d'afficher le formulaire d'ajout d'un Trade.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue trade/add.
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
	Trade newTrade = new Trade();
	model.addAttribute("trade", newTrade);
	return "trade/add";
    }

    /**
     * La méthode validate permet de poster le formulaire d'ajout d'un Trade.
     * @param trade le model de l'entité Trade à sauvegarder en base de données.
     * @param result binding result afin de vérifier la conformité des informations
     * du Trade.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue trade/list si l'ajout est un succès, trade/add si
     * une erreur est rencontrée.
     */
    @PostMapping("/trade/validate")
    public String validate(@ModelAttribute("trade") @Valid Trade trade, BindingResult result,
	    Principal principal, Model model) {
	// TODO: check data valid and save to db, after saving return Trade list
	boolean resultAdd;
	resultAdd = tradeService.addTrade(trade);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page trade/add avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service d'ajout d'un
	 * Trade renvoit true pour confirmer l'ajout de celui-ci, nous renvoyons
	 * la page trade/list avec un message de succès pour indiquer que la sauvegarde
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer l'ajout du Trade alors nous renvoyons la page trade/add
	 * avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "trade/add";
	} else if (resultAdd == true) {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("success", "Successful trade addition");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "trade/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "trade/add";
	}
    }

    /**
     * La méthode showUpdateForm permet d'afficher le formulaire de mis à jour
     * d'un Trade.
     * @param id l'id du Trade que l'on souhaite modifier.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue trade/update.
     */
    @GetMapping("/trade/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
	// TODO: get Trade by Id and to model then show to the form
	Trade trade = tradeService.getTradeById(id);
	model.addAttribute("trade", trade);
	return "trade/update";
    }

    /**
     * La méthode updateTrade permet de poster la mis à jour d'un Trade.
     * @param id l'id du Trade à modifier.
     * @param trade le model de l'entité Trade.
     * @param result binding result afin de vérifier la conformité des informations
     * du Trade.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue trade/list si la mis à jour est validée, trade/update
     * si une erreur est rencontrée.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") int id, @ModelAttribute("trade") @Valid Trade trade,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update Trade and return
	boolean resultUpdate;
	resultUpdate = tradeService.updateTrade(id, trade);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page trade/update avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service de mis à jour d'un
	 * Trade renvoit true pour confirmer la modification de celui-ci, nous renvoyons
	 * la page trade/list avec un message de succès pour indiquer que la mis à jour
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer la mis à jour du Trade alors nous renvoyons la page
	 * trade/update avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "trade/update";
	} else if (resultUpdate == true) {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "trade/list";
	} else {
	    Trade tradeModel = tradeService.getTradeById(id);
	    model.addAttribute("trade", tradeModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "trade/update";
	}
    }

    /**
     * La méthode deleteTrade permet de supprimer un Trade depuis la liste.
     * @param id l'id du Trade à supprimer.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue trade/list.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") int id, Principal principal, Model model) {
	// TODO: Find Trade by Id and delete the Trade, return to Trade list
	boolean resultDelete;
	resultDelete = tradeService.deleteTrade(id);

	/*
	 * Si le service deleteTrade nous renvoit true, alors nous affichons la
	 * liste mis à jour avec un message de succès pour indiquer la validation
	 * de la suppression du Trade, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le service deleteTrade nous renvoit false, alors nous affichons un
	 * message d'erreur au dessus de la liste afin d'indiquer que la suppression
	 * n'est pas validée, la encore on récupère l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 */
	if (resultDelete == true) {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "trade/list";
	} else {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "trade/list";
	}
    }
}
