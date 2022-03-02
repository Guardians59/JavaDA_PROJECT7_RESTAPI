package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.services.ICurvePointService;
import com.nnk.springboot.services.IOAuthService;

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
 * La classe CurveController est le controller qui permet de gérer les URL
 * curvePoint, afin d'afficher la liste des CurvePoint, ajouter, modifier et
 * supprimer un CurvePoint.
 * 
 * @author Dylan
 *
 */
@Controller
public class CurveController {

    @Autowired
    ICurvePointService curvePointService;

    @Autowired
    IOAuthService oauthService;

    /**
     * La méthode home permet d'afficher la liste des CurvePoint enregistrés en base
     * de données.
     * @param principal les informations de l'utilisateur connécté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue curvePoint/list.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Principal principal, Model model) {
	/*
	 * On récupère la liste des CurvePoint via le service curvePointService, on
	 * indique le model curvePoint avec l'attribut correspondant qui va permettre
	 * à thymeleaf d'afficher celle-ci.
	 * On récupère l'username de l'utilisateur connecté avec l'objet LoggedUsername
	 * qui utilise le service oauthService.
	 * Nous renvoyons la vue curvePoint/list.
	 */
	List<CurvePoint> curvePointList = new ArrayList<>();
	curvePointList = curvePointService.getAllCurvePoint();
	model.addAttribute("curvePoint", curvePointList);
	LoggedUsername logged = new LoggedUsername();
	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	model.addAttribute("loggedusername", logged);
	return "curvePoint/list";
    }

    /**
     * La méthode addBidForm permet d'afficher le formulaire d'ajout d'un CurvePoint.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue curvePoint/add.
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
	CurvePoint curvePoint = new CurvePoint();
	model.addAttribute("curvePoint", curvePoint);
	return "curvePoint/add";
    }

    /**
     * La méthode validate permet de poster le formulaire d'ajout d'un CurvePoint.
     * @param curvePoint le model de l'entité CurvePoint à sauvegarder en base de données.
     * @param result binding result afin de vérifier la conformité des informations
     * du CurvePoint.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue curvePoint/list si l'ajout est un succès, curvePoint/add
     * si une erreur est rencontrée.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@ModelAttribute("curvePoint") @Valid CurvePoint curvePoint, BindingResult result,
	    Principal principal, Model model) {
	// TODO: check data valid and save to db, after saving return Curve list
	boolean resultAdd;
	resultAdd = curvePointService.addCurvePoint(curvePoint);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page curvePoint/add avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service d'ajout d'un
	 * CurvePoint renvoit true pour confirmer l'ajout de celui-ci, nous renvoyons
	 * la page curvePoint/list avec un message de succès pour indiquer que la sauvegarde
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer l'ajout du CurvePoint alors nous renvoyons la page curvePoint/add
	 * avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "curvePoint/add";
	} else if (resultAdd == true) {
	    List<CurvePoint> curvePointList = new ArrayList<>();
	    curvePointList = curvePointService.getAllCurvePoint();
	    model.addAttribute("curvePoint", curvePointList);
	    model.addAttribute("success", "Successful curve addition");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "curvePoint/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "curvePoint/add";
	}
    }

    /**
     * La méthode showUpdateForm permet d'afficher le formulaire de mis à jour
     * d'un CurvePoint.
     * @param id l'id du BidList que l'on souhaite modifier.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue curvePoint/update.
     */
    @GetMapping("/curvePoint/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
	// TODO: get CurvePoint by Id and to model then show to the form
	CurvePoint curvePoint = new CurvePoint();
	curvePoint = curvePointService.getCurvePointById(id);
	model.addAttribute("curvePoint", curvePoint);
	return "curvePoint/update";
    }

    /**
     * La méthode updateCurve permet de poster la mis à jour d'un CurvePoint.
     * @param id l'id du CurvePoint à modifier.
     * @param curvePoint le model de l'entité CurvePoint.
     * @param result binding result afin de vérifier la conformité des informations
     * du CurvePoint.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue curvePoint/list si la mis à jour est validée, curvePoint/update
     * si une erreur est rencontrée.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") int id, @ModelAttribute("curvePoint") @Valid CurvePoint curvePoint,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update Curve and return
	boolean resultUpdate;
	resultUpdate = curvePointService.updateCurvePoint(id, curvePoint);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page curvePoint/update avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service de mis à jour d'un
	 * CurvePoint renvoit true pour confirmer la modification de celui-ci, nous renvoyons
	 * la page curvePoint/list avec un message de succès pour indiquer que la mis à jour
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer la mis à jour du CurvePoint alors nous renvoyons la page
	 * curvePoint/update avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "curvePoint/update";
	} else if (resultUpdate == true) {
	    List<CurvePoint> curvePointList = new ArrayList<>();
	    curvePointList = curvePointService.getAllCurvePoint();
	    model.addAttribute("curvePoint", curvePointList);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "curvePoint/list";
	} else {
	    CurvePoint curvePointModel = curvePointService.getCurvePointById(id);
	    model.addAttribute("curvePoint", curvePointModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "curvePoint/update";
	}

    }

    /**
     * La méthode deleteCurve permet de supprimer un CurvePoint depuis la liste.
     * @param id l'id du CurvePoint à supprimer.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue curvePoint/list.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") int id, Principal principal, Model model) {
	// TODO: Find Curve by Id and delete the Curve, return to Curve list
	boolean resultDelete;
	resultDelete = curvePointService.deleteCurvePoint(id);

	/*
	 * Si le service deleteCurvePoint nous renvoit true, alors nous affichons la
	 * liste mis à jour avec un message de succès pour indiquer la validation
	 * de la suppression du CurvePoint, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le service deleteCurvePoint nous renvoit false, alors nous affichons un
	 * message d'erreur au dessus de la liste afin d'indiquer que la suppression
	 * n'est pas validée, la encore on récupère l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 */
	if (resultDelete == true) {
	    List<CurvePoint> curvePoint = new ArrayList<>();
	    curvePoint = curvePointService.getAllCurvePoint();
	    model.addAttribute("curvePoint", curvePoint);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "curvePoint/list";
	} else {
	    List<CurvePoint> curvePoint = new ArrayList<>();
	    curvePoint = curvePointService.getAllCurvePoint();
	    model.addAttribute("bidList", curvePoint);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    LoggedUsername logged = new LoggedUsername();
	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "curvePoint/list";
	}

    }
}
