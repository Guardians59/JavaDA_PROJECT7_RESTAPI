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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.ITradeService;

@Service
public class TradeServiceImpl implements ITradeService {

    @Autowired
    TradeRepository tradeRepository;

    private static Logger logger = LogManager.getLogger(TradeServiceImpl.class);

    @Override
    public List<Trade> getAllTrade() {
	List<Trade> listTrade = new ArrayList<>();
	listTrade = tradeRepository.findAll();
	if (listTrade.isEmpty()) {
	    logger.info("The list of trade is empty");
	} else {
	    logger.info("List of trade successfully recovered");
	}
	return listTrade;
    }

    @Override
    @Transactional
    public boolean addTrade(Trade newTrade) {
	boolean result = false;
	if (!newTrade.getAccount().isEmpty() && !newTrade.getType().isEmpty() && newTrade.getBuyQuantity() > 0) {

	    Trade trade = new Trade();
	    trade.setAccount(newTrade.getAccount());
	    trade.setType(newTrade.getType());
	    trade.setBuyQuantity(newTrade.getBuyQuantity());
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    trade.setCreationDate(timestamp);
	    tradeRepository.save(trade);
	    result = true;
	    logger.info("The new trade is registered successfully");
	} else {
	    logger.error("A data in the form is missing");
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateTrade(int id, Trade trade) {
	boolean result = false;
	Optional<Trade> searchTrade = tradeRepository.findById(id);
	
	if (searchTrade.isPresent()) {
	    if(!trade.getAccount().isEmpty() && !trade.getType().isEmpty() && trade.getBuyQuantity() > 0) {
		Trade tradeUpdate = new Trade();
		tradeUpdate = searchTrade.get();
		tradeUpdate.setAccount(trade.getAccount());
		tradeUpdate.setType(trade.getType());
		tradeUpdate.setBuyQuantity(trade.getBuyQuantity());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		tradeUpdate.setRevisionDate(timestamp);
		tradeRepository.save(tradeUpdate);
		result = true;
		logger.info("Updated trade with id number " + id + " successfully");
	    } else {
		logger.error("A data in the form is missing");
	    }

	} else {
	    logger.error("No trade found with id number " + id);
	    }
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteTrade(int id) {
	boolean result = false;
	Optional<Trade> searchTrade = tradeRepository.findById(id);
	
	if (searchTrade.isPresent()) {
	    tradeRepository.deleteById(id);
	    result = true;
	    logger.info("The trade with id number " + id + " has been deleted correctly");
	} else {
	    logger.error("No trade found with id number " + id);
	}
	return result;
    }

    @Override
    public Trade getTradeById(int id) {
	Optional<Trade> searchTrade = tradeRepository.findById(id);
	Trade trade = new Trade();
	
	if (searchTrade.isPresent()) {
	    trade = searchTrade.get();
	    logger.info("The trade with id number " + id + " successfully recovered");
	} else {
	    logger.error("No trade found with id number " + id);
	}
	return trade;
    }

}
