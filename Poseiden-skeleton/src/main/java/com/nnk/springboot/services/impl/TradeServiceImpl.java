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

/**
 * La classe TradeServiceImpl est l'implémentation de l'interface ITradeService.
 * 
 * @see ITradeService
 * @author Dylan
 *
 */
@Service
public class TradeServiceImpl implements ITradeService {

    @Autowired
    TradeRepository tradeRepository;

    private static Logger logger = LogManager.getLogger(TradeServiceImpl.class);

    @Override
    public List<Trade> getAllTrade() {
	/*
	 * On instancie une liste qui va récupérer les Trade en base de données.
	 */
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
	/*
	 * On vérifie que les informations du Trade entrés dans le formulaire HTML
	 * soient bien présentes, si tel est le cas on instancie un nouvel objet
	 * Trade afin de lui donner les informations récupérées et de le sauvegarder
	 * en base de données, tout en indiquant au boolean de renvoyer true pour
	 * confirmer que la sauvegarde est effective, si des données sont manquantes
	 * lors de la vérification des informations alors le boolean reste false.
	 * 
	 */
	if (!newTrade.getAccount().isEmpty() && !newTrade.getType().isEmpty() 
		&& (newTrade.getBuyQuantity() != null && newTrade.getBuyQuantity() > 0)) {

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
	/*
	 * On instancie un Trade optional afin de vérifier qu'il existe bien un
	 * Trade sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<Trade> searchTrade = tradeRepository.findById(id);
	Trade tradeUpdate = new Trade();
	/*
	 * On vérifie qu'un Trade est bien présent en base de donnée, ensuite
	 * nous vérifions que les informations du Trade mis à jour soient bien
	 * présentes et conformes, si tout ceci est correct nous sauvegardons les
	 * modifications des informations du Trade et nous passons le boolean
	 * en true afin d'indiquer que la mis à jour est validée, si une condition
	 * est non remplie alors le boolean reste sur false afin d'indiquer que
	 * la mis à jour n'est pas validée.
	 */
	if (searchTrade.isPresent()) {
	    if(!trade.getAccount().isEmpty() && !trade.getType().isEmpty() 
		    && (trade.getBuyQuantity() != null && trade.getBuyQuantity() > 0)) {
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
	/*
	 * On instancie un Trade optional afin de vérifier qu'il existe bien un
	 * Trade sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<Trade> searchTrade = tradeRepository.findById(id);
	/*
	 * On vérifie qu'un Trade est bien présent avec l'id indiqué, si tel
	 * est le cas nous supprimons celui-ci et indiquons true au boolean afin
	 * d'indiquer que la suppression est validée, si aucun Trade n'est
	 * trouvé alors nous laissons le boolean sur false afin d'indiquer que
	 * la suppression n'est pas validée. 
	 */
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
	/*
	 * On instancie un Trade optional afin de vérifier qu'il existe bien un
	 * Trade sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<Trade> searchTrade = tradeRepository.findById(id);
	Trade trade = new Trade();
	/*
	 * On vérifie qu'un Trade est bien présent avec l'id indiqué, si tel
	 * est le cas nous récupérons les informations de celui-ci dans un
	 * nouvel objet Trade, sinon le Trade retourné reste à null.
	 */
	if (searchTrade.isPresent()) {
	    trade = searchTrade.get();
	    logger.info("The trade with id number " + id + " successfully recovered");
	} else {
	    logger.error("No trade found with id number " + id);
	}
	return trade;
    }

}
