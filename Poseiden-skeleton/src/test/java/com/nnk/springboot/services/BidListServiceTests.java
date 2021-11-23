package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class BidListServiceTests {

    @Autowired
    IBidListService bidListService;

    @Autowired
    BidListRepository bidListRepository;

    @Test
    @Order(1)
    public void getAllBidListServiceTest() {
	//WHEN
	List<BidList> listBidList = bidListService.getAllBidList();
	//THEN
	assertEquals(listBidList.isEmpty(), false);
    }
    @Test
    @Order(2)
    public void addNewBidServiceTest() {
	// GIVEN
	boolean result;
	List<BidList> listResult = bidListRepository.findAll();
	int sizeBeforeSave = listResult.size();
	BidList newBidList = new BidList();
	newBidList.setAccount("Account Test");
	newBidList.setType("Type Test");
	newBidList.setBidQuantity(20.0);
	// WHEN
	result = bidListService.addBidList(newBidList);
	listResult = bidListRepository.findAll();
	// THEN
	assertEquals(result, true);
	assertEquals(listResult.size(), sizeBeforeSave + 1);
    }
    
    @Test
    @Order(3)
    public void addNewBidServiceErrorTest() {
	// GIVEN
	boolean result;
	List<BidList> listResult = bidListRepository.findAll();
	int sizeBeforeSave = listResult.size();
	BidList newBidList = new BidList();
	newBidList.setAccount("Account Test");
	newBidList.setType("Type Test");
	newBidList.setBidQuantity(0.0);
	// WHEN
	result = bidListService.addBidList(newBidList);
	listResult = bidListRepository.findAll();
	// THEN
	assertEquals(result, false);
	assertEquals(listResult.size(), sizeBeforeSave);
    }

    @Test
    @Order(4)
    public void updateBidServiceTest() {
	// GIVEN
	boolean result;
	List<BidList> listResult = bidListRepository.findAll();
	int numberOfBid = listResult.size();
	int index = numberOfBid - 1;
	BidList bidListUpdate = new BidList();
	bidListUpdate = listResult.get(index);
	int id = bidListUpdate.getBidListId();
	bidListUpdate.setBidQuantity(17.0);
	// WHEN
	result = bidListService.updateBidList(id, bidListUpdate);
	Optional<BidList> bidList = bidListRepository.findById(id);
	BidList bidListAfterUpdate = bidList.get();
	Double bidQuantity = bidListAfterUpdate.getBidQuantity();
	// THEN
	assertEquals(result, true);
	assertEquals(bidQuantity, 17.0);
    }
    
    @Test
    @Order(5)
    public void updateBidServiceErrorTest() {
	// GIVEN
	boolean result;
	List<BidList> listResult = bidListRepository.findAll();
	int numberOfBid = listResult.size();
	int index = numberOfBid - 1;
	BidList bidListUpdate = new BidList();
	bidListUpdate = listResult.get(index);
	int id = bidListUpdate.getBidListId();
	bidListUpdate.setBidQuantity(0.0);
	// WHEN
	result = bidListService.updateBidList(id, bidListUpdate);
	Optional<BidList> bidList = bidListRepository.findById(id);
	BidList bidListAfterUpdate = bidList.get();
	Double bidQuantity = bidListAfterUpdate.getBidQuantity();
	// THEN
	assertEquals(result, false);
	assertEquals(bidQuantity, 17.0);
    }

    @Test
    @Order(6)
    public void deleteBidServiceTest() {
	// GIVEN
	boolean result;
	List<BidList> listResult = bidListRepository.findAll();
	int numberOfBid = listResult.size();
	int index = numberOfBid - 1;
	BidList bidListDelete = new BidList();
	bidListDelete = listResult.get(index);
	int id = bidListDelete.getBidListId();
	// WHEN
	result = bidListService.deleteBidList(id);
	Optional<BidList> bidList = bidListRepository.findById(id);
	// THEN
	assertEquals(result, true);
	assertEquals(bidList.isPresent(), false);
    }
    
    @Test
    @Order(7)
    public void deleteBidServiceErrorTest() {
	// GIVEN
	boolean result;
	int falseId = 2;
	// WHEN
	result = bidListService.deleteBidList(falseId);
	// THEN
	assertEquals(result, false);
    }
    
    @Test
    @Order(8)
    public void getBidByIdServiceTest() {
	//GIVEN
	BidList bidList = new BidList();
	//WHEN
	bidList = bidListService.getBidById(20);
	//THEN
	assertEquals(bidList.getAccount(), "user");
    }
    
    @Test
    @Order(9)
    public void getBidByIdServiceErrorTest() {
	//GIVEN
	BidList bidList = new BidList();
	//WHEN
	bidList = bidListService.getBidById(2);
	//THEN
	assertEquals(bidList.getAccount(), null);
    }

}
