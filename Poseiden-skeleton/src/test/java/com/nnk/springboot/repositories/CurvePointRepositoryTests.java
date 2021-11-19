package com.nnk.springboot.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.nnk.springboot.domain.CurvePoint;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CurvePointRepositoryTests {

    @Autowired
    CurvePointRepository curvePointRepository;
    
    @Test
    @Order(1)
    public void getListAllCurvePointTest() {
	//WHEN
	List<CurvePoint> curvePointList = curvePointRepository.findAll();
	//THEN
	assertTrue(curvePointList.size() > 0);
    }
    
    @Test
    @Order(2)
    public void saveCurvePointTest() {
	//GIVEN
	CurvePoint curvePoint = new CurvePoint();
	curvePoint.setCurveId(100);
	curvePoint.setTerm(16.0);
	curvePoint.setValue(10.0);
	List<CurvePoint> resultList = curvePointRepository.findAll();
	int numberOfCurve = resultList.size();
	//WHEN
	curvePointRepository.save(curvePoint);
	resultList = curvePointRepository.findAll();
	int numberOfCurveAfterSave = resultList.size();
	//THEN
	assertEquals(numberOfCurveAfterSave, numberOfCurve + 1);
    }
    
    @Test
    @Order(3)
    public void updateCurvePointTest() {
	//GIVEN
	List<CurvePoint> resultList = curvePointRepository.findAll();
	int numberOfCurve = resultList.size();
	int index = numberOfCurve - 1;
	CurvePoint curvePointUpdate = new CurvePoint();
	curvePointUpdate = resultList.get(index);
	curvePointUpdate.setTerm(17.0);
	//WHEN
	curvePointRepository.save(curvePointUpdate);
	resultList = curvePointRepository.findAll();
	Double term = resultList.get(index).getTerm();
	//THEN
	assertEquals(numberOfCurve, resultList.size());
	assertEquals(term, 17.0);
    }
    
    @Test
    @Order(4)
    public void deleteCurvePointTest() {
	//GIVEN
	CurvePoint curvePointDelete = new CurvePoint();
	List<CurvePoint> resultList = curvePointRepository.findAll();
	int size = resultList.size();
	int index = size - 1;
	curvePointDelete = resultList.get(index);
	//WHEN
	curvePointRepository.deleteById(curvePointDelete.getId());
	resultList = curvePointRepository.findAll();
	Optional<CurvePoint> curvePoint = curvePointRepository.findById(curvePointDelete.getId());
	//THEN
	assertEquals(resultList.size(), size - 1);
	assertEquals(curvePoint.isPresent(), false);
    }
}
