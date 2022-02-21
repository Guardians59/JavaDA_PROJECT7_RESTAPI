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

@Controller
public class CurveController {

    @Autowired
    ICurvePointService curvePointService;

    @Autowired
    IOAuthService oauthService;

    @RequestMapping("/curvePoint/list")
    public String home(Principal principal, Model model) {
	// TODO: find all Curve Point, add to model
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

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
	CurvePoint curvePoint = new CurvePoint();
	model.addAttribute("curvePoint", curvePoint);
	return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@ModelAttribute("curvePoint") @Valid CurvePoint curvePoint, BindingResult result,
	    Principal principal, Model model) {
	// TODO: check data valid and save to db, after saving return Curve list
	boolean resultAdd;
	resultAdd = curvePointService.addCurvePoint(curvePoint);

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

    @GetMapping("/curvePoint/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
	// TODO: get CurvePoint by Id and to model then show to the form
	CurvePoint curvePoint = new CurvePoint();
	curvePoint = curvePointService.getCurvePointById(id);
	model.addAttribute("curvePoint", curvePoint);
	return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") int id, @ModelAttribute("curvePoint") @Valid CurvePoint curvePoint,
	    BindingResult result, Principal principal, Model model) {
	// TODO: check required fields, if valid call service to update Curve and return
	// Curve list
	boolean resultUpdate;
	resultUpdate = curvePointService.updateCurvePoint(id, curvePoint);

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

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") int id, Principal principal, Model model) {
	// TODO: Find Curve by Id and delete the Curve, return to Curve list
	boolean resultDelete;
	resultDelete = curvePointService.deleteCurvePoint(id);

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
