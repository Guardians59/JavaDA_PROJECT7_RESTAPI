package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IOAuthService;
import com.nnk.springboot.services.IRuleNameService;

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
 * La classe RuleNameController est le controller qui permet de gérer les URL
 * ruleName, afin d'afficher la liste, ajouter, modifier et supprimer un RuleName.
 * 
 * @author Dylan
 *
 */
@Controller
public class RuleNameController {

    @Autowired
    IRuleNameService ruleNameService;

    @Autowired
    IOAuthService oauthService;

    /**
     * La méthode home permet d'afficher la liste des RuleName enregistrés en base
     * de données.
     * @param principal les informations de l'utilisateur connécté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue ruleName/list.
     */
    @RequestMapping("/ruleName/list")
    public String home(Principal principal, Model model) {
	/*
	 * On récupère la liste des RuleName via le service ruleNameService, on
	 * indique le model ruleName avec l'attribut correspondant qui va permettre
	 * à thymeleaf d'afficher celle-ci.
	 * On récupère l'username de l'utilisateur connecté avec l'objet LoggedUsername
	 * qui utilise le service oauthService.
	 * Nous renvoyons la vue ruleName/list.
	 */
	List<RuleName> listRuleName = new ArrayList<>();
	listRuleName = ruleNameService.getAllRuleName();
	model.addAttribute("ruleName", listRuleName);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	model.addAttribute("loggedusername", logged);
	return "ruleName/list";
    }

    /**
     * La méthode addRuleForm permet d'afficher le formulaire d'ajout d'un RuleName.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue ruleName/add.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
	RuleName ruleName = new RuleName();
	model.addAttribute("ruleName", ruleName);
	return "ruleName/add";
    }

    /**
     * La méthode validate permet de poster le formulaire d'ajout d'un RuleName.
     * @param ruleName le model de l'entité RuleName à sauvegarder en base de données.
     * @param result binding result afin de vérifier la conformité des informations
     * du BidList.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue ruleName/list si l'ajout est un succès, ruleName/add si
     * une erreur est rencontrée.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@ModelAttribute("ruleName") @Valid RuleName ruleName, BindingResult result,
	    Principal principal, Model model) {
	// TODO: check data valid and save to db, after saving return RuleName list
	boolean resultAdd;
	resultAdd = ruleNameService.addRuleName(ruleName);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page ruleName/add avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service d'ajout d'un
	 * RuleName renvoit true pour confirmer l'ajout de celui-ci, nous renvoyons
	 * la page ruleName/list avec un message de succès pour indiquer que la sauvegarde
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer l'ajout du RuleName alors nous renvoyons la page ruleName/add
	 * avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "ruleName/add";
	} else if (resultAdd == true) {
	    List<RuleName> listRuleName = new ArrayList<>();
	    listRuleName = ruleNameService.getAllRuleName();
	    model.addAttribute("ruleName", listRuleName);
	    model.addAttribute("success", "Successful rule addition");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "ruleName/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "ruleName/add";
	}

    }

    /**
     * La méthode showUpdateForm permet d'afficher le formulaire de mis à jour
     * d'un RuleName.
     * @param id l'id du RuleName que l'on souhaite modifier.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue ruleName/update.
     */
    @GetMapping("/ruleName/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	// TODO: get RuleName by Id and to model then show to the form
	RuleName ruleName = ruleNameService.getRuleNameById(id);
	model.addAttribute("ruleName", ruleName);
	return "ruleName/update";
    }

    /**
     * La méthode updateRuleName permet de poster la mis à jour d'un RuleName.
     * @param id l'id du RuleName modifié.
     * @param ruleName le model de l'entité RuleName.
     * @param result binding result afin de vérifier la conformité des informations
     * du RuleName.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue ruleName/list si la mis à jour est validée, ruleName/update
     * si une erreur est rencontrée.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @ModelAttribute("ruleName") @Valid RuleName ruleName,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update RuleName and
	boolean resultUpdate;
	resultUpdate = ruleNameService.updateRuleName(id, ruleName);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page ruleName/update avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service de mis à jour d'un
	 * RuleName renvoit true pour confirmer la modification de celui-ci, nous renvoyons
	 * la page ruleName/list avec un message de succès pour indiquer que la mis à jour
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer la mis à jour du RuleName alors nous renvoyons la page
	 * ruleName/update avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "ruleName/update";
	} else if (resultUpdate == true) {
	    List<RuleName> listRuleName = new ArrayList<>();
	    listRuleName = ruleNameService.getAllRuleName();
	    model.addAttribute("ruleName", listRuleName);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "ruleName/list";
	} else {
	    RuleName ruleNameModel = ruleNameService.getRuleNameById(id);
	    model.addAttribute("ruleName", ruleNameModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "ruleName/update";
	}
    }

    /**
     * La méthode deleteRuleName permet de supprimer un RuleName depuis la liste.
     * @param id l'id du RuleName à supprimer.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue ruleName/list.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Principal principal, Model model) {
	// TODO: Find RuleName by Id and delete the RuleName, return to Rule list
	boolean resultDelete;
	resultDelete = ruleNameService.deleteRuleName(id);

	/*
	 * Si le service deleteRuleName nous renvoit true, alors nous affichons la
	 * liste mis à jour avec un message de succès pour indiquer la validation
	 * de la suppression du RuleName, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le service deleteRuleName nous renvoit false, alors nous affichons un
	 * message d'erreur au dessus de la liste afin d'indiquer que la suppression
	 * n'est pas validée, la encore on récupère l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 */
	if (resultDelete == true) {
	    List<RuleName> listRuleName = new ArrayList<>();
	    listRuleName = ruleNameService.getAllRuleName();
	    model.addAttribute("ruleName", listRuleName);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "ruleName/list";
	} else {
	    List<RuleName> listRuleName = new ArrayList<>();
	    listRuleName = ruleNameService.getAllRuleName();
	    model.addAttribute("ruleName", listRuleName);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "ruleName/list";
	}
    }
}
