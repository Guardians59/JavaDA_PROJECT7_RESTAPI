package com.nnk.springboot.services;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CurvePointServiceTests {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Autowired
    ICurvePointService curvePointService;

    @Test
    @Order(1)
    public void getAllCurvePointServiceTest() {
	// WHEN
	List<CurvePoint> listCurvePoint = curvePointService.getAllCurvePoint();
	// THEN
	assertEquals(listCurvePoint.isEmpty(), false);
    }

    @Test
    @Order(2)
    public void addNewCurvePointServiceTest() {
	// GIVEN
	boolean result;
	List<CurvePoint> listResult = curvePointRepository.findAll();
	int sizeBeforeSave = listResult.size();
	CurvePoint curvePoint = new CurvePoint();
	curvePoint.setCurveId(90);
	curvePoint.setTerm(20.0);
	curvePoint.setValue(12.0);
	// WHEN
	result = curvePointService.addCurvePoint(curvePoint);
	listResult = curvePointRepository.findAll();
	// THEN
	assertEquals(result, true);
	assertEquals(listResult.size(), sizeBeforeSave + 1);
    }

    @Test
    @Order(3)
    public void addNewCurvePointServiceErrorTest() {
	// GIVEN
	boolean result;
	List<CurvePoint> listResult = curvePointRepository.findAll();
	int sizeBeforeSave = listResult.size();
	CurvePoint curvePoint = new CurvePoint();
	curvePoint.setCurveId(90);
	curvePoint.setTerm(0.0);
	curvePoint.setValue(12.0);
	// WHEN
	result = curvePointService.addCurvePoint(curvePoint);
	listResult = curvePointRepository.findAll();
	// THEN
	assertEquals(result, false);
	assertEquals(listResult.size(), sizeBeforeSave);

    }

    @Test
    @Order(4)
    public void updateCurvePointServiceTest() {
	// GIVEN
	boolean result;
	List<CurvePoint> listResult = curvePointRepository.findAll();
	int numberOfCurve = listResult.size();
	int index = numberOfCurve - 1;
	CurvePoint curvePointUpdate = new CurvePoint();
	curvePointUpdate = listResult.get(index);
	int id = curvePointUpdate.getId();
	curvePointUpdate.setTerm(17.0);
	// WHEN
	result = curvePointService.updateCurvePoint(id, curvePointUpdate);
	Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
	CurvePoint curvePointAfterUpdate = curvePoint.get();
	Double term = curvePointAfterUpdate.getTerm();
	// THEN
	assertEquals(result, true);
	assertEquals(term, 17.0);
    }

    @Test
    @Order(5)
    public void updateCurvePointServiceErrorTest() {
	// GIVEN
	boolean result;
	List<CurvePoint> listResult = curvePointRepository.findAll();
	int numberOfCurve = listResult.size();
	int index = numberOfCurve - 1;
	CurvePoint curvePointUpdate = new CurvePoint();
	curvePointUpdate = listResult.get(index);
	int id = curvePointUpdate.getId();
	curvePointUpdate.setTerm(0.0);
	// WHEN
	result = curvePointService.updateCurvePoint(id, curvePointUpdate);
	Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
	CurvePoint curvePointAfterUpdate = curvePoint.get();
	Double term = curvePointAfterUpdate.getTerm();
	// THEN
	assertEquals(result, false);
	assertEquals(term, 17.0);
    }

    @Test
    @Order(6)
    public void deleteCurvePointServiceTest() {
	// GIVEN
	boolean result;
	List<CurvePoint> listResult = curvePointRepository.findAll();
	int numberOfCurve = listResult.size();
	int index = numberOfCurve - 1;
	CurvePoint curvePointDelete = new CurvePoint();
	curvePointDelete = listResult.get(index);
	int id = curvePointDelete.getId();
	// WHEN
	result = curvePointService.deleteCurvePoint(id);
	Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
	// THEN
	assertEquals(result, true);
	assertEquals(curvePoint.isPresent(), false);
    }
    
    @Test
    @Order(7)
    public void deleteCurvePointServiceErrorTest() {
	// GIVEN
	boolean result;
	int falseId = 1;
	// WHEN
	result = curvePointService.deleteCurvePoint(falseId);
	// THEN
	assertEquals(result, false);
    }
    
    @Test
    @Order(8)
    public void getCurvePointByIdServiceTest() {
	//GIVEN
	CurvePoint curvePoint = new CurvePoint();
	//WHEN
	curvePoint = curvePointService.getCurvePointById(12);
	//THEN
	assertEquals(curvePoint.getCurveId(), 12);
    }
    
    @Test
    @Order(9)
    public void getCurvePointByIdServiceErrorTest() {
	//GIVEN
	CurvePoint curvePoint = new CurvePoint();
	//WHEN
	curvePoint = curvePointService.getCurvePointById(2);
	//THEN
	assertFalse(curvePoint.getCurveId() > 0);
    }

}
