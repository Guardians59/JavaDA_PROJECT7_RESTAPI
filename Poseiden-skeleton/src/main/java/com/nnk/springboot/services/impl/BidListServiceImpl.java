package com.nnk.springboot.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;

@Service
public class BidListServiceImpl implements IBidListService {

    @Autowired
    BidListRepository bidListRepository;

    private static Logger logger = LogManager.getLogger(BidListServiceImpl.class);

    @Override
    public List<BidList> getAllBidList() {
	List<BidList> listBid = new ArrayList<>();
	listBid = bidListRepository.findAll();
	if (listBid.isEmpty()) {
	    logger.info("The list of bidList is empty");
	} else {
	    logger.info("List of bidList successfully recovered");
	}
	return listBid;
    }
    
    @Override
    @Transactional
    public boolean addBidList(BidList newBidList) {
	boolean result = false;
	if (!newBidList.getAccount().isEmpty() && !newBidList.getType().isEmpty() && newBidList.getBidQuantity() > 0) {

	    BidList bidListAdd = new BidList();
	    bidListAdd.setAccount(newBidList.getAccount());
	    bidListAdd.setType(newBidList.getType());
	    bidListAdd.setBidQuantity(newBidList.getBidQuantity());
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    bidListAdd.setCreationDate(timestamp);
	    bidListRepository.save(bidListAdd);
	    result = true;
	    logger.info("The new bidList is registered successfully");
	} else {
	    logger.error("A data in the form is missing");
	}

	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateBidList(int id, BidList bidList) {
	boolean result = false;
	Optional<BidList> searchBidListUpdate;
	searchBidListUpdate = bidListRepository.findById(id);
	BidList bidListUpdate = new BidList();
	
	if (searchBidListUpdate.isPresent()) {
	    if (!bidList.getAccount().isEmpty() && !bidList.getType().isEmpty() 
		    && bidList.getBidQuantity() > 0) {
		bidListUpdate = searchBidListUpdate.get();
		bidListUpdate.setAccount(bidList.getAccount());
		bidListUpdate.setType(bidList.getType());
		bidListUpdate.setBidQuantity(bidList.getBidQuantity());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		bidListUpdate.setRevisionDate(timestamp);
		bidListRepository.save(bidListUpdate);
		result = true;
		logger.info("Updated bidList with id number " + id + " successfully");
	    } else {
		logger.error("A data in the form is missing");
	    } 

	} else {
	    logger.error("No bidList found with id number " + id);
	}

	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteBidList(int id) {
	boolean result = false;
	Optional<BidList> bidListDelete;
	bidListDelete = bidListRepository.findById(id);
	
	if (bidListDelete.isPresent()) {
	    bidListRepository.deleteById(id);
	    result = true;
	    logger.info("The bidList with id number " + id + " has been deleted correctly");
	} else {
	    logger.error("No bidList found with id number " + id);
	}
	
	return result;
    }

    @Override
    public BidList getBidById(int id) {
	Optional<BidList> bidListUpdate;
	bidListUpdate = bidListRepository.findById(id);
	BidList bidList = new BidList();
	
	if(!bidListUpdate.isPresent()) {
	    logger.error("No bidList found with id number " + id);
	} else {
	    logger.info("The bidList with id number " + id + " successfully recovered");
	    bidList = bidListUpdate.get();
	}
	return bidList;
    }

}
