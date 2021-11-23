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

@Service
public class CurvePointServiceImpl implements ICurvePointService {

    @Autowired
    CurvePointRepository curvePointRepository;

    private static Logger logger = LogManager.getLogger(CurvePointServiceImpl.class);

    @Override
    public List<CurvePoint> getAllCurvePoint() {
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
	if (newCurvePoint.getCurveId() > 0 && newCurvePoint.getTerm() > 0 && newCurvePoint.getValue() > 0) {
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
	CurvePoint curvePointUpdate = new CurvePoint();
	curvePointUpdate = curvePointRepository.getById(id);

	if (curvePointUpdate != null) {
	    if (curvePoint.getCurveId() > 0 && curvePoint.getTerm() > 0 && curvePoint.getValue() > 0) {
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
	Optional<CurvePoint> curvePointDelete;
	curvePointDelete = curvePointRepository.findById(id);

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
	Optional<CurvePoint> searchCurvePoint;
	searchCurvePoint = curvePointRepository.findById(id);
	CurvePoint curvePoint = new CurvePoint();
	
	if (!searchCurvePoint.isPresent()) {
	    logger.error("No curvePoint found with id number " + id);
	} else {
	    curvePoint = searchCurvePoint.get();
	    logger.info("The curvePoint with id number " + id + " successfully recovered");
	}
	return curvePoint;
    }

}
