package com.nnk.springboot.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.IRatingService;

/**
 * La classe RatingServiceImpl est l'implémentation de l'interface IRatingService.
 * 
 * @see IRatingService
 * @author Dylan
 *
 */
@Service
public class RatingServiceImpl implements IRatingService{
    
    @Autowired
    RatingRepository ratingRepository;
    
    private static Logger logger = LogManager.getLogger(RatingServiceImpl.class);

    @Override
    public List<Rating> getAllRating() {
	/*
	 * On instancie une liste qui va récupérer les Rating en base de données.
	 */
	List<Rating> ratingList = new ArrayList<>();
	ratingList = ratingRepository.findAll();
	if (ratingList.isEmpty()) {
	    logger.info("The list of rating is empty");
	} else {
	    logger.info("List of rating successfully recovered");
	}
	return ratingList;
    }

    @Override
    @Transactional
    public boolean addRating(Rating newRating) {
	boolean result = false;
	/*
	 * On vérifie que les informations du Rating entrés dans le formulaire HTML
	 * soient bien présentes, si tel est le cas on instancie un nouvel objet
	 * Rating afin de lui donner les informations récupérées et de le sauvegarder
	 * en base de données, tout en indiquant au boolean de renvoyer true pour
	 * confirmer que la sauvegarde est effective, si des données sont manquantes
	 * lors de la vérification des informations alors le boolean reste false.
	 * 
	 */
	if(!newRating.getMoodysRating().isEmpty() && !newRating.getSandPRating().isEmpty()
		&& !newRating.getFitchRating().isEmpty() && newRating.getOrderNumber() > 0) {
	    
	    Rating rating = new Rating();
	    rating.setMoodysRating(newRating.getMoodysRating());
	    rating.setSandPRating(newRating.getSandPRating());
	    rating.setFitchRating(newRating.getFitchRating());
	    rating.setOrderNumber(newRating.getOrderNumber());
	    ratingRepository.save(rating);
	    result = true;
	    logger.info("The new rating is registered successfully");
	} else {
	    logger.error("A data in the form is missing");
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateRating(int id, Rating rating) {
	boolean result = false;
	/*
	 * On instancie un Rating optional afin de vérifier qu'il existe bien un
	 * Rating sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Rating ratingUpdate = new Rating();
	Optional<Rating> searchRating;
	searchRating = ratingRepository.findById(id);
	/*
	 * On vérifie qu'un Rating est bien présent en base de donnée, ensuite
	 * nous vérifions que les informations du Rating mis à jour soient bien
	 * présentes et conformes, si tout ceci est correct nous sauvegardons les
	 * modifications des informations du Rating et nous passons le boolean
	 * en true afin d'indiquer que la mis à jour est validée, si une condition
	 * est non remplie alors le boolean reste sur false afin d'indiquer que
	 * la mis à jour n'est pas validée.
	 */
	if (searchRating.isPresent()) {
	    if (!rating.getMoodysRating().isEmpty() && !rating.getSandPRating().isEmpty()
		    && !rating.getFitchRating().isEmpty() && rating.getOrderNumber() > 0) {
		ratingUpdate = searchRating.get();
		ratingUpdate.setMoodysRating(rating.getMoodysRating());
		ratingUpdate.setSandPRating(rating.getSandPRating());
		ratingUpdate.setFitchRating(rating.getFitchRating());
		ratingUpdate.setOrderNumber(rating.getOrderNumber());
		ratingRepository.save(ratingUpdate);
		result = true;
		logger.info("Updated rating with id number " + id + " successfully");
	    } else {
		logger.error("A data in the form is missing");
	    } 
	} else {
	    logger.error("No rating found with id number " + id);
	    }
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteRating(int id) {
	boolean result = false;
	/*
	 * On instancie un Rating optional afin de vérifier qu'il existe bien un
	 * Rating sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<Rating> ratingDelete;
	ratingDelete = ratingRepository.findById(id);
	/*
	 * On vérifie qu'un Rating est bien présent avec l'id indiqué, si tel
	 * est le cas nous supprimons celui-ci et indiquons true au boolean afin
	 * d'indiquer que la suppression est validée, si aucun Rating n'est
	 * trouvé alors nous laissons le boolean sur false afin d'indiquer que
	 * la suppression n'est pas validée. 
	 */
	if (ratingDelete.isPresent()) {
	    ratingRepository.deleteById(id);
	    result = true;
	    logger.info("The rating with id number " + id + " has been deleted correctly");
	} else {
	    logger.error("No rating found with id number " + id);
	}
	return result;
    }

    @Override
    public Rating getRatingById(int id) {
	/*
	 * On instancie un Rating optional afin de vérifier qu'il existe bien un
	 * Rating sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<Rating> searchRating;
	searchRating = ratingRepository.findById(id);
	Rating rating = new Rating();
	/*
	 * On vérifie qu'un Rating est bien présent avec l'id indiqué, si tel
	 * est le cas nous récupérons les informations de celui-ci dans un
	 * nouvel objet Rating, sinon le Rating retourné reste à null.
	 */
	if (searchRating.isPresent()) {
	    rating = searchRating.get();
	    logger.info("The rating with id number " + id + " successfully recovered");
	} else {
	    logger.error("No rating found with id number " + id);
	}
	return rating;
    }

}
