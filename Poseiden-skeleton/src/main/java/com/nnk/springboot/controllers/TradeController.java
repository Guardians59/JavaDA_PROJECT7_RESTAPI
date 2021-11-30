package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
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

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class TradeController {

    @Autowired
    ITradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
	// TODO: find all Trade, add to model
	List<Trade> listTrade = new ArrayList<>();
	listTrade = tradeService.getAllTrade();
	model.addAttribute("trade", listTrade);
	return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
	Trade newTrade = new Trade();
	model.addAttribute("trade", newTrade);
	return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@ModelAttribute("trade") @Valid Trade trade, BindingResult result, Model model) {
	// TODO: check data valid and save to db, after saving return Trade list
	boolean resultAdd;
	resultAdd = tradeService.addTrade(trade);

	if (result.hasErrors()) {
	    return "trade/add";
	} else if (resultAdd == true) {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("success", "Successful trade addition");
	    return "trade/list";
	} else {
	    model.addAttribute("error",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "trade/add";
	}
    }

    @GetMapping("/trade/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
	// TODO: get Trade by Id and to model then show to the form
	Trade trade = tradeService.getTradeById(id);
	model.addAttribute("trade", trade);
	return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") int id, @ModelAttribute("trade") @Valid Trade trade, BindingResult result, Model model) {
	// TODO: check required fields, if valid call service to update Trade and return
	// Trade list
	boolean resultUpdate;
	resultUpdate = tradeService.updateTrade(id, trade);
	
	if (result.hasErrors()) {
	    return "trade/update";
	} else if (resultUpdate == true) {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    return "trade/list";
	} else {
	    Trade tradeModel = tradeService.getTradeById(id);
	    model.addAttribute("trade", tradeModel);
	    model.addAttribute("updateError",
		    "An error has occured, check that you have filled in all the information fields and try again");
	    return "trade/update";
	}
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") int id, Model model) {
	// TODO: Find Trade by Id and delete the Trade, return to Trade list
	boolean resultDelete;
	resultDelete = tradeService.deleteTrade(id);
	
	if (resultDelete == true) {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    return "trade/list";
	} else {
	    List<Trade> listTrade = new ArrayList<>();
	    listTrade = tradeService.getAllTrade();
	    model.addAttribute("trade", listTrade);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    return "trade/list";
	}
    }
}
