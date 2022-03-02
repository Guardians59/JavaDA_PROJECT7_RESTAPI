package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.LoggedUsername;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IOAuthService;
import com.nnk.springboot.services.IUserService;

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
 * La classe UserController est le controller qui permet de gérer les URL user,
 * afin d'afficher la liste, ajouter, modifier et supprimer un User.
 * 
 * @author Dylan
 *
 */
@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IOAuthService oauthService;

    /**
     * La méthode home permet d'afficher la liste des User enregistrés en base
     * de données.
     * @param principal les informations de l'utilisateur connécté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue user/list.
     */
    @RequestMapping("/user/list")
    public String home(Principal principal, Model model) {
	/*
	 * On récupère la liste des User via le service userService, on
	 * indique le model user avec l'attribut correspondant qui va permettre
	 * à thymeleaf d'afficher celle-ci.
	 * On récupère l'username de l'utilisateur connecté avec l'objet LoggedUsername
	 * qui utilise le service oauthService.
	 * Nous renvoyons la vue user/list.
	 */
	List<User> listUser = new ArrayList<>();
	listUser = userService.getAllUser();
	model.addAttribute("user", listUser);
	LoggedUsername logged = new LoggedUsername();

	if (oauthService.getOauthUsername(principal).getUsername() != null) {
	    logged = oauthService.getOauthUsername(principal);
	} else {
	    logged = oauthService.getUsername(principal);
	}
	model.addAttribute("loggedusername", logged);
	return "user/list";
    }

    /**
     * La méthode addUserForm permet d'afficher le formulaire d'ajout d'un User.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue user/add.
     */
    @GetMapping("/user/add")
    public String addUserForm(Model model) {
	User user = new User();
	model.addAttribute("user", user);
	return "user/add";
    }

    /**
     * La méthode validate permet de poster le formulaire d'ajout d'un User.
     * @param user le model de l'entité BidList à sauvegarder en base de données.
     * @param result binding result afin de vérifier la conformité des informations
     * du User.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue user/list si l'ajout est un succès, user/add si
     * une erreur est rencontrée.
     */
    @PostMapping("/user/validate")
    public String validate(@ModelAttribute("user") @Valid User user, BindingResult result,
	    Principal principal, Model model) {
	boolean resultAdd;
	resultAdd = userService.addUser(user);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page user/add avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service d'ajout d'un
	 * User renvoit true pour confirmer l'ajout de celui-ci, nous renvoyons
	 * la page user/list avec un message de succès pour indiquer que la sauvegarde
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer l'ajout du User alors nous renvoyons la page user/add
	 * avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "user/add";
	} else if (resultAdd == true) {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("success", "Successful user addition");
	    LoggedUsername logged = new LoggedUsername();

	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "user/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "user/add";
	}
    }

    /**
     * La méthode showUpdateForm permet d'afficher le formulaire de mis à jour
     * d'un User.
     * @param id l'id du User que l'on souhaite modifier.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue user/update.
     */
    @GetMapping("/user/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
	User user = userService.getUserById(id);
	model.addAttribute("user", user);
	return "user/update";
    }

    /**
     * La méthode updateUser permet de poster la mis à jour d'un User.
     * @param id l'id du User à modifier.
     * @param user le model de l'entité User.
     * @param result binding result afin de vérifier la conformité des informations
     * du User.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue user/list si la mis à jour est validée, user/update
     * si une erreur est rencontrée.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute("user") @Valid User user,
	    BindingResult result, Principal principal, Model model) {
	boolean resultUpdate;
	resultUpdate = userService.updateUser(id, user);

	/*
	 * On vérifie avec le BindingResult que les données de l'entité soient valides,
	 * auquel cas nous renvoyons la page user/update avec le message d'erreur du
	 * binding correspondant à la donnée erronée.
	 * Si le binding ne contient pas d'erreur et que le service de mis à jour d'un
	 * User renvoit true pour confirmer la modification de celui-ci, nous renvoyons
	 * la page user/list avec un message de succès pour indiquer que la mis à jour
	 * c'est bien exécutée, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le binding ne renvoit pas d'erreur et que le boolean ne renvoit pas true
	 * pour confirmer la mis à jour du User alors nous renvoyons la page
	 * user/update avec un message d'erreur.
	 */
	if (result.hasErrors()) {
	    return "user/update";
	} else if (resultUpdate == true) {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    LoggedUsername logged = new LoggedUsername();

	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "user/list";
	} else {
	    User userModel = userService.getUserById(id);
	    model.addAttribute("user", userModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "user/update";
	}
    }

    /**
     * La méthode deleteUser permet de supprimer un User depuis la liste.
     * @param id l'id du User à supprimer.
     * @param principal les informations de l'utilisateur connecté.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue user/list.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Principal principal, Model model) {
	boolean resultDelete;
	resultDelete = userService.deleteUser(id);

	/*
	 * Si le service deleteUser nous renvoit true, alors nous affichons la
	 * liste mis à jour avec un message de succès pour indiquer la validation
	 * de la suppression du User, on récupère également l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 * Si le service deleteUser nous renvoit false, alors nous affichons un
	 * message d'erreur au dessus de la liste afin d'indiquer que la suppression
	 * n'est pas validée, la encore on récupère l'username de l'utilisateur
	 * connecté avec l'objet LoggedUsername qui utilise le service oauthService.
	 */
	if (resultDelete == true) {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    LoggedUsername logged = new LoggedUsername();

	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "user/list";
	} else {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    LoggedUsername logged = new LoggedUsername();

	    if (oauthService.getOauthUsername(principal).getUsername() != null) {
		logged = oauthService.getOauthUsername(principal);
	    } else {
		logged = oauthService.getUsername(principal);
	    }
	    model.addAttribute("loggedusername", logged);
	    return "user/list";
	}
    }
}
