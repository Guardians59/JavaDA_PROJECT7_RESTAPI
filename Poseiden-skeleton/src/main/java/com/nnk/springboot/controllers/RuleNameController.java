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

@Controller
public class RuleNameController {

    @Autowired
    IRuleNameService ruleNameService;

    @Autowired
    IOAuthService oauthService;

    @RequestMapping("/ruleName/list")
    public String home(Principal principal, Model model) {
	// TODO: find all RuleName, add to model
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

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
	RuleName ruleName = new RuleName();
	model.addAttribute("ruleName", ruleName);
	return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@ModelAttribute("ruleName") @Valid RuleName ruleName, BindingResult result,
	    Principal principal, Model model) {
	// TODO: check data valid and save to db, after saving return RuleName list
	boolean resultAdd;
	resultAdd = ruleNameService.addRuleName(ruleName);

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

    @GetMapping("/ruleName/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	// TODO: get RuleName by Id and to model then show to the form
	RuleName ruleName = ruleNameService.getRuleNameById(id);
	model.addAttribute("ruleName", ruleName);
	return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @ModelAttribute("ruleName") @Valid RuleName ruleName,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update RuleName and
	// return RuleName list
	boolean resultUpdate;
	resultUpdate = ruleNameService.updateRuleName(id, ruleName);

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

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Principal principal, Model model) {
	// TODO: Find RuleName by Id and delete the RuleName, return to Rule list
	boolean resultDelete;
	resultDelete = ruleNameService.deleteRuleName(id);

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
