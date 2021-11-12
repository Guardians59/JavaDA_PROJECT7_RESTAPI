package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

public interface IBidListService {
    
    public List<BidList> getAllBidList();
    
    public boolean addBidList (BidList newBidList);
    
    public boolean updateBidList (int id, BidList bidList);
    
    public boolean deleteBidList (int id);
    
    public BidList getBidById (int id);

}
