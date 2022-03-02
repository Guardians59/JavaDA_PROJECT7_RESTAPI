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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.ICurvePointService;

/**
 * La classe CurvePointServiceImpl est l'implémentation de l'interface
 * ICurvePointService.
 * 
 * @see ICurvePointService
 * @author Dylan
 *
 */
@Service
public class CurvePointServiceImpl implements ICurvePointService {

    @Autowired
    CurvePointRepository curvePointRepository;

    private static Logger logger = LogManager.getLogger(CurvePointServiceImpl.class);

    @Override
    public List<CurvePoint> getAllCurvePoint() {
	/*
	 * On instancie une liste qui va récupérer les CurvePoint en base de
	 * données.
	 */
	List<CurvePoint> curvePointList = new ArrayList<>();
	curvePointList = curvePointRepository.findAll();
	if (curvePointList.isEmpty()) {
	    logger.info("The list of curvePoint is empty");
	} else {
	    logger.info("List of curvePoint successfully recovered");
	}
	return curvePointList;
    }

    @Override
    @Transactional
    public boolean addCurvePoint(CurvePoint newCurvePoint) {
	boolean result = false;
	/*
	 * On vérifie que les informations du CurvePoint entrés dans le formulaire HTML
	 * soient bien présentes, si tel est le cas on instancie un nouvel objet
	 * CurvePoint afin de lui donner les informations récupérées et de le sauvegarder
	 * en base de données, tout en indiquant au boolean de renvoyer true pour
	 * confirmer que la sauvegarde est effective, si des données sont manquantes
	 * lors de la vérification des informations alors le boolean reste false.
	 * 
	 */
	if (newCurvePoint.getCurveId() > 0 && newCurvePoint.getTerm() > 0
		&& newCurvePoint.getValue() > 0) {
	    CurvePoint curvePoint = new CurvePoint();
	    curvePoint.setCurveId(newCurvePoint.getCurveId());
	    curvePoint.setTerm(newCurvePoint.getTerm());
	    curvePoint.setValue(newCurvePoint.getValue());
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    curvePoint.setCreationDate(timestamp);
	    curvePointRepository.save(curvePoint);
	    result = true;
	    logger.info("The new curvePoint is registered successfully");
	} else {
	    logger.error("A data in the form is missing");
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateCurvePoint(int id, CurvePoint curvePoint) {
	boolean result = false;
	/*
	 * On instancie un CurvePoint optional afin de vérifier qu'il existe bien un
	 * CurvePoint sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<CurvePoint> searchCurvePointUpdate;
	searchCurvePointUpdate = curvePointRepository.findById(id);
	CurvePoint curvePointUpdate = new CurvePoint();
	/*
	 * On vérifie qu'un CurvePoint est bien présent en base de donnée, ensuite
	 * nous vérifions que les informations du CurvePoint mis à jour soient bien
	 * présentes et conformes, si tout ceci est correct nous sauvegardons les
	 * modifications des informations du CurvePoint et nous passons le boolean
	 * en true afin d'indiquer que la mis à jour est validée, si une condition
	 * est non remplie alors le boolean reste sur false afin d'indiquer que
	 * la mis à jour n'est pas validée.
	 */
	if (searchCurvePointUpdate.isPresent()) {
	    if (curvePoint.getCurveId() > 0 && curvePoint.getTerm() > 0 
		    && curvePoint.getValue() > 0) {
		curvePointUpdate = searchCurvePointUpdate.get();
		curvePointUpdate.setCurveId(curvePoint.getCurveId());
		curvePointUpdate.setTerm(curvePoint.getTerm());
		curvePointUpdate.setValue(curvePoint.getValue());
		curvePointRepository.save(curvePointUpdate);
		result = true;
		logger.info("Updated curvePoint with id number " + id + " successfully");
	    } else {
		logger.error("A data in the form is missing");
	    }
	} else {
	    logger.error("No curvePoint found with id number " + id);
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteCurvePoint(int id) {
	boolean result = false;
	/*
	 * On instancie un CurvePoint optional afin de vérifier qu'il existe bien un
	 * CurvePoint sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<CurvePoint> curvePointDelete;
	curvePointDelete = curvePointRepository.findById(id);
	/*
	 * On vérifie qu'un CurvePoint est bien présent avec l'id indiqué, si tel
	 * est le cas nous supprimons celui-ci et indiquons true au boolean afin
	 * d'indiquer que la suppression est validée, si aucun CurvePoint n'est
	 * trouvé alors nous laissons le boolean sur false afin d'indiquer que
	 * la suppression n'est pas validée. 
	 */
	if (curvePointDelete.isPresent()) {
	    curvePointRepository.deleteById(id);
	    result = true;
	    logger.info("The curvePoint with id number " + id + " has been deleted correctly");
	} else {
	    logger.error("No curvePoint found with id number " + id);
	}

	return result;
    }

    @Override
    public CurvePoint getCurvePointById(int id) {
	/*
	 * On instancie un CurvePoint optional afin de vérifier qu'il existe bien un
	 * CurvePoint sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<CurvePoint> searchCurvePoint;
	searchCurvePoint = curvePointRepository.findById(id);
	CurvePoint curvePoint = new CurvePoint();
	/*
	 * On vérifie qu'un CurvePoint est bien présent avec l'id indiqué, si tel
	 * est le cas nous récupérons les informations de celui-ci dans un
	 * nouvel objet CurvePoint, sinon le BidList retourné reste à null.
	 */
	if (!searchCurvePoint.isPresent()) {
	    logger.error("No curvePoint found with id number " + id);
	} else {
	    curvePoint = searchCurvePoint.get();
	    logger.info("The curvePoint with id number " + id + " successfully recovered");
	}
	return curvePoint;
    }

}
