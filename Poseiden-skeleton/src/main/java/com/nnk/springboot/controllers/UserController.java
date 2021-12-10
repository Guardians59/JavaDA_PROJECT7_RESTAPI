package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IPasswordValid;
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

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IPasswordValid passwordValid;

    @RequestMapping("/user/list")
    public String home(Model model) {
	List<User> listUser = new ArrayList<>();
	listUser = userService.getAllUser();
	model.addAttribute("user", listUser);
	return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
	User user = new User();
	model.addAttribute("user", user);
	return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
	boolean resultAdd;
	boolean passwordIsValid;
	passwordIsValid = passwordValid.isPasswordValid(user.getPassword());
	resultAdd = userService.addUser(user);

	if (result.hasErrors()) {
	    return "user/add";
	} else if (resultAdd == true) {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("success", "Successful user addition");
	    return "user/list";
	} else if (passwordIsValid == false) {
	    model.addAttribute("errorPassword",
		    "Le mot de passe doit contenir un minimum de 8 caractères, dont un chiffre, une majuscule et un symbole");
	    return "user/add";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "user/add";
	}
    }

    @GetMapping("/user/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
	User user = userService.getUserById(id);
	model.addAttribute("user", user);
	return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") int id,@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
	boolean resultUpdate;
	boolean passwordIsValid;
	passwordIsValid = passwordValid.isPasswordValid(user.getPassword());
	resultUpdate = userService.updateUser(id, user);
	
	if (result.hasErrors()) {
	    return "user/update";
	} else if (resultUpdate == true) {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    return "user/list";
	} else if (passwordIsValid == false) {
	    model.addAttribute("errorPassword",
		    "Le mot de passe doit contenir un minimum de 8 caractères, dont un chiffre, une majuscule et un symbole");
	    return "user/update";
	} else {
	    User userModel = userService.getUserById(id);
	    model.addAttribute("user", userModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "user/update";
	}
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
	boolean resultDelete;
	resultDelete = userService.deleteUser(id);
	
	if (resultDelete == true) {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    return "user/list";
	} else {
	    List<User> listUser = new ArrayList<>();
	    listUser = userService.getAllUser();
	    model.addAttribute("user", listUser);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    return "user/list";
	}
    }
}
