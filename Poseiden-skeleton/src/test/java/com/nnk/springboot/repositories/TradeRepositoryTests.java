package com.nnk.springboot.repositories;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Trade;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TradeRepositoryTests {
    
    @Autowired
    TradeRepository tradeRepository;
    
    @Test
    @Order(1)
    public void getAllTradeTest() {
	//WHEN
	List<Trade> listTrade = tradeRepository.findAll();
	//THEN
	assertTrue(listTrade.size() > 0);
    }
    
    @Test
    @Order(2)
    public void saveTradeTest() {
	//GIVEN
	Trade trade = new Trade();
	trade.setAccount("User Test");
	trade.setType("Test User");
	trade.setBuyQuantity(2.0);
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	trade.setCreationDate(timestamp);
	List<Trade> listTrade = tradeRepository.findAll();
	int numberOfTrade = listTrade.size();
	//WHEN
	tradeRepository.save(trade);
	listTrade = tradeRepository.findAll();
	int numberOfTradeAfterSave = listTrade.size();
	//THEN
	assertEquals(numberOfTradeAfterSave, numberOfTrade + 1);
    }
    
    @Test
    @Order(3)
    public void updateTradeTest() {
	//GIVEN
	List<Trade> listTrade = tradeRepository.findAll();
	int numberOfTrade = listTrade.size();
	int index = numberOfTrade - 1;
	Trade tradeUpdate = new Trade();
	tradeUpdate = listTrade.get(index);
	tradeUpdate.setBuyQuantity(3.0);
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	tradeUpdate.setRevisionDate(timestamp);
	//WHEN
	tradeRepository.save(tradeUpdate);
	listTrade = tradeRepository.findAll();
	Double buyQuantity = listTrade.get(index).getBuyQuantity();
	//THEN
	assertEquals(numberOfTrade, listTrade.size());
	assertEquals(buyQuantity, 3.0);
    }
    
    @Test
    @Order(4)
    public void deleteTradeTest() {
	//GIVEN
	Trade tradeDelete = new Trade();
	List<Trade> listTrade = tradeRepository.findAll();
	int size = listTrade.size();
	int index = size - 1;
	tradeDelete = listTrade.get(index);
	//WHEN
	tradeRepository.delete(tradeDelete);
	listTrade = tradeRepository.findAll();
	Optional<Trade> trade = tradeRepository.findById(tradeDelete.getTradeId());
	//THEN
	assertEquals(trade.isPresent(), false);
	assertEquals(listTrade.size(), size - 1);
	
    }

}
