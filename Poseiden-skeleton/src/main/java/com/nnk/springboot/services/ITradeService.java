package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;

public interface ITradeService {
    
    public List<Trade> getAllTrade();
    
    public boolean addTrade(Trade newTrade);
    
    public boolean updateTrade(int id, Trade trade);
    
    public boolean deleteTrade(int id);
    
    public Trade getTradeById(int id);

}
