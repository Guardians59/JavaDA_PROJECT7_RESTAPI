package com.nnk.springboot.services;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TradeServiceTests {
    
    @Autowired
    TradeRepository tradeRepository;
    
    @Autowired
    ITradeService tradeService;
    
    @Test
    @Order(1)
    public void getAllTradeServiceTest() {
	//WHEN
	List<Trade> listTrade = tradeService.getAllTrade();
	//THEN
	assertEquals(listTrade.isEmpty(), false);
    }
    
    @Test
    @Order(2)
    public void addNewTradeServiceTest() {
	//GIVEN
	boolean result;
	List<Trade> listTrade = tradeRepository.findAll();
	int sizeBeforeSave = listTrade.size();
	Trade trade = new Trade();
	trade.setAccount("User Test Service");
	trade.setType("Service Test User");
	trade.setBuyQuantity(4.0);
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	trade.setCreationDate(timestamp);
	//WHEN
	result = tradeService.addTrade(trade);
	listTrade = tradeRepository.findAll();
	//THEN
	assertEquals(result, true);
	assertEquals(listTrade.size(), sizeBeforeSave + 1);
    }
    
    @Test
    @Order(3)
    public void addNewTradeServiceErrorTest() {
	//GIVEN
	boolean result;
	List<Trade> listTrade = tradeRepository.findAll();
	int sizeBeforeSave = listTrade.size();
	Trade trade = new Trade();
	trade.setAccount("User Test Service");
	trade.setType("Service Test User");
	trade.setBuyQuantity(0.0);
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	trade.setCreationDate(timestamp);
	//WHEN
	result = tradeService.addTrade(trade);
	listTrade = tradeRepository.findAll();
	//THEN
	assertEquals(result, false);
	assertEquals(listTrade.size(), sizeBeforeSave);
    }
    
    @Test
    @Order(4)
    public void updateTradeServiceTest() {
	//GIVEN
	boolean result;
	List<Trade> listTrade = tradeRepository.findAll();
	int numberOfTrade = listTrade.size();
	int index = numberOfTrade - 1;
	Trade tradeUpdate = new Trade();
	tradeUpdate = listTrade.get(index);
	int id = tradeUpdate.getTradeId();
	tradeUpdate.setBuyQuantity(1.0);
	//WHEN
	result = tradeService.updateTrade(id, tradeUpdate);
	listTrade = tradeRepository.findAll();
	Double buyQuantity = listTrade.get(index).getBuyQuantity();
	//THEN
	assertEquals(result, true);
	assertEquals(buyQuantity, 1.0);
    }
    
    @Test
    @Order(5)
    public void updateTradeServiceErrorTest() {
	//GIVEN
	boolean result;
	List<Trade> listTrade = tradeRepository.findAll();
	int numberOfTrade = listTrade.size();
	int index = numberOfTrade - 1;
	Trade tradeUpdate = new Trade();
	tradeUpdate = listTrade.get(index);
	int id = tradeUpdate.getTradeId();
	tradeUpdate.setBuyQuantity(2.0);
	tradeUpdate.setAccount("");
	//WHEN
	result = tradeService.updateTrade(id, tradeUpdate);
	listTrade = tradeRepository.findAll();
	Double buyQuantity = listTrade.get(index).getBuyQuantity();
	//THEN
	assertEquals(result, false);
	assertEquals(buyQuantity, 1.0);
    }
    
    @Test
    @Order(6)
    public void deleteTradeServiceTest() {
	//GIVEN
	boolean result;
	List<Trade> listTrade = tradeRepository.findAll();
	int numberOfTrade = listTrade.size();
	int index = numberOfTrade - 1;
	Trade tradeDelete = new Trade();
	tradeDelete = listTrade.get(index);
	int id = tradeDelete.getTradeId();
	//WHEN
	result = tradeService.deleteTrade(id);
	Optional<Trade> trade = tradeRepository.findById(id);
	//THEN
	assertEquals(result, true);
	assertEquals(trade.isPresent(), false);
    }
    
    @Test
    @Order(7)
    public void deleteTradeServiceErrorTest() {
	//GIVEN
	boolean result;
	int falseId = 3;
	List<Trade> listTrade = tradeRepository.findAll();
	int numberOfTrade = listTrade.size();
	//WHEN
	result = tradeService.deleteTrade(falseId);
	listTrade = tradeRepository.findAll();
	//THEN
	assertEquals(result, false);
	assertEquals(listTrade.size(), numberOfTrade);
    }
    
    @Test
    @Order(8)
    public void getTradeByIdServiceTest() {
	//GIVEN
	Trade trade = new Trade();
	//WHEN
	trade = tradeService.getTradeById(1);
	//THEN
	assertEquals(trade.getAccount(), "user");
	assertEquals(trade.getBuyQuantity(), 2.0);
    }
    
    @Test
    @Order(9)
    public void getTradeByIdServiceErrorTest() {
	//GIVEN
	Trade trade = new Trade();
	//WHEN
	trade = tradeService.getTradeById(3);
	//THEN
	assertEquals(trade.getAccount(), null);
    }

}
