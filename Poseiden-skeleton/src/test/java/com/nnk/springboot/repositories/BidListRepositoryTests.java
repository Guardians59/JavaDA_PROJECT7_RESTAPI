package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class BidListRepositoryTests {

    @Autowired
    private BidListRepository bidListRepository;

    /*
     * @Test public void bidListTest() { BidList bid = new BidList();
     * bid.setAccount("Account Test"); bid.setType("Type test"); bid.setBid(10d);
     * 
     * // Save bid = bidListRepository.save(bid); assertNotNull(bid.getBidListId());
     * assertEquals(bid.getBidQuantity(), 10d, 10d);
     * 
     * // Update bid.setBidQuantity(20d); bid = bidListRepository.save(bid);
     * assertEquals(bid.getBidQuantity(), 20d, 20d);
     * 
     * // Find List<BidList> listResult = bidListRepository.findAll();
     * assertTrue(listResult.size() > 0);
     * 
     * // Delete Integer id = bid.getBidListId(); bidListRepository.delete(bid);
     * Optional<BidList> bidList = bidListRepository.findById(id);
     * assertFalse(bidList.isPresent()); }
     */
    @Test
    @Order(1)
    public void getListAllBidTest() {
	// WHEN
	List<BidList> listResult = bidListRepository.findAll();
	// THEN
	assertTrue(listResult.size() > 0);

    }

    @Test
    @Order(2)
    public void saveAndUpdateBidTest() {
	// GIVEN
	BidList newBidList = new BidList();
	newBidList.setAccount("Account Test");
	newBidList.setType("Type Test");
	newBidList.setBidQuantity(20.0);
	List<BidList> listResult = bidListRepository.findAll();
	int numberOfBid = listResult.size();
	// WHEN
	bidListRepository.save(newBidList);
	listResult = bidListRepository.findAll();
	int numberOfBidAfterSave = listResult.size();
	// THEN
	assertEquals(numberOfBidAfterSave, numberOfBid + 1);

	// GIVEN
	int index = numberOfBidAfterSave - 1;
	BidList bidListUpdate = new BidList();
	bidListUpdate = listResult.get(index);
	bidListUpdate.setBidQuantity(15.0);
	// WHEN
	bidListRepository.save(bidListUpdate);
	listResult = bidListRepository.findAll();
	Double bidQuantity = listResult.get(index).getBidQuantity();
	// THEN
	assertEquals(bidQuantity, 15.0);
	assertEquals(listResult.size(), numberOfBidAfterSave);
    }

    @Test
    @Order(3)
    public void deleteBidTest() {
	// GIVEN
	BidList bidListDelete = new BidList();
	List<BidList> listResult = bidListRepository.findAll();
	int size = listResult.size();
	int index = size - 1;
	bidListDelete = listResult.get(index);
	// WHEN
	bidListRepository.deleteById(bidListDelete.getBidListId());
	listResult = bidListRepository.findAll();
	Optional<BidList> bidList = bidListRepository.findById(bidListDelete.getBidListId());
	// THEN
	assertEquals(listResult.size(), size - 1);
	assertEquals(bidList.isPresent(), false);
    }
}
