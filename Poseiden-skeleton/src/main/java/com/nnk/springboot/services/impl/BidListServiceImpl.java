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

/**
 * La classe BidListServiceImpl est l'implémentation de l'interface IBidListService.
 * 
 * @see IBidListService
 * @author Dylan
 *
 */
@Service
public class BidListServiceImpl implements IBidListService {

    @Autowired
    BidListRepository bidListRepository;

    private static Logger logger = LogManager.getLogger(BidListServiceImpl.class);

    @Override
    public List<BidList> getAllBidList() {
	/*
	 * On instancie une liste qui va récupérer les BidList en base de données.
	 */
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
	/*
	 * On vérifie que les informations du BidList entrés dans le formulaire HTML
	 * soient bien présentes, si tel est le cas on instancie un nouvel objet
	 * BidList afin de lui donner les informations récupérées et de le sauvegarder
	 * en base de données, tout en indiquant au boolean de renvoyer true pour
	 * confirmer que la sauvegarde est effective, si des données sont manquantes
	 * lors de la vérification des informations alors le boolean reste false.
	 * 
	 */
	if (!newBidList.getAccount().isEmpty() && !newBidList.getType().isEmpty()
		&& newBidList.getBidQuantity() > 0) {

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
	/*
	 * On instancie un BidList optional afin de vérifier qu'il existe bien un
	 * BidList sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<BidList> searchBidListUpdate;
	searchBidListUpdate = bidListRepository.findById(id);
	BidList bidListUpdate = new BidList();
	/*
	 * On vérifie qu'un BidList est bien présent en base de donnée, ensuite
	 * nous vérifions que les informations du BidList mis à jour soient bien
	 * présentes et conformes, si tout ceci est correct nous sauvegardons les
	 * modifications des informations du BidList et nous passons le boolean
	 * en true afin d'indiquer que la mis à jour est validée, si une condition
	 * est non remplie alors le boolean reste sur false afin d'indiquer que
	 * la mis à jour n'est pas validée.
	 */
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
	/*
	 * On instancie un BidList optional afin de vérifier qu'il existe bien un
	 * BidList sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<BidList> bidListDelete;
	bidListDelete = bidListRepository.findById(id);
	/*
	 * On vérifie qu'un BidList est bien présent avec l'id indiqué, si tel
	 * est le cas nous supprimons celui-ci et indiquons true au boolean afin
	 * d'indiquer que la suppression est validée, si aucun BidList n'est
	 * trouvé alors nous laissons le boolean sur false afin d'indiquer que
	 * la suppression n'est pas validée. 
	 */
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
	/*
	 * On instancie un BidList optional afin de vérifier qu'il existe bien un
	 * BidList sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<BidList> bidListUpdate;
	bidListUpdate = bidListRepository.findById(id);
	BidList bidList = new BidList();
	/*
	 * On vérifie qu'un BidList est bien présent avec l'id indiqué, si tel
	 * est le cas nous récupérons les informations de celui-ci dans un
	 * nouvel objet BidList, sinon le BidList retourné reste à null.
	 */
	if(!bidListUpdate.isPresent()) {
	    logger.error("No bidList found with id number " + id);
	} else {
	    logger.info("The bidList with id number " + id + " successfully recovered");
	    bidList = bidListUpdate.get();
	}
	return bidList;
    }

}
