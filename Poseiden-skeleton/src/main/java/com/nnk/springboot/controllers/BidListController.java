package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class BidListController {

    @Autowired
    IBidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
	// TODO: call service find all bids to show to the view
	List<BidList> bidList = new ArrayList<>();
	bidList = bidListService.getAllBidList();
	model.addAttribute("bidList", bidList);
	return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
	BidList bidList = new BidList();
	model.addAttribute("bidList", bidList);

	return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
	// TODO: check data valid and save to db, after saving return bid list
	boolean resultAdd;
	resultAdd = bidListService.addBidList(bid);

	if (result.hasErrors()) {
	    BidList bidList = new BidList();
	    model.addAttribute("bidList", bidList);
	    return "bidList/add";
	} else if (resultAdd == true) {
	    List<BidList> bidList = new ArrayList<>();
	    bidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", bidList);
	    model.addAttribute("success", "Successful bid addition");
	    return "bidList/list";
	} else {
	    model.addAttribute("error", "An error has occured please try again");
	    return "bidList/add";
	}
    }

    @GetMapping("/bidList/edit/{bidListId}")
    public String showUpdateForm(@PathVariable("bidListId") int id, Model model) {
	// TODO: get Bid by Id and to model then show to the form
	BidList bidList = bidListService.getBidById(id);
	model.addAttribute("bidList", bidList);
	return "bidList/update";
    }

    @PostMapping("/bidList/update/{bidListId}")
    public String updateBid(@PathVariable("bidListId") int id, @Valid BidList bidList, BindingResult result,
	    Model model) {
	// TODO: check required fields, if valid call service to update Bid and return
	// list Bid
	boolean resultUpdate;
	resultUpdate = bidListService.updateBidList(id, bidList);

	if (result.hasErrors()) {
	    BidList bidListModel = bidListService.getBidById(id);
	    model.addAttribute("bidList", bidListModel);
	    return "bidList/update";
	} else if (resultUpdate == true) {
	    List<BidList> listBidList = new ArrayList<>();
	    listBidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", listBidList);
	    model.addAttribute("updateSuccess", "The update was executed successfully");
	    return "/bidList/list";
	} else {
	    BidList bidListModel = bidListService.getBidById(id);
	    model.addAttribute("bidList", bidListModel);
	    model.addAttribute("updateError", "An error has occured please try again");
	    return "bidList/update";
	}

    }

    @GetMapping("/bidList/delete/{bidListId}")
    public String deleteBid(@PathVariable("bidListId") Integer id, Model model) {
	// TODO: Find Bid by Id and delete the bid, return to Bid list
	boolean resultDelete;
	resultDelete = bidListService.deleteBidList(id);

	if (resultDelete == true) {
	    List<BidList> bidList = new ArrayList<>();
	    bidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", bidList);
	    model.addAttribute("deleteSuccess", "The delete was executed successfully");
	    return "/bidList/list";
	} else {
	    List<BidList> bidList = new ArrayList<>();
	    bidList = bidListService.getAllBidList();
	    model.addAttribute("bidList", bidList);
	    model.addAttribute("deleteError", "An error has occured please try again");
	    return "/bidList/list";
	}
    }
}
