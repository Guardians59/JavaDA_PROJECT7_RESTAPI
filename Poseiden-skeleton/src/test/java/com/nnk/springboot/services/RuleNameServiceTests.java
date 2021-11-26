package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RuleNameServiceTests {
    
    @Autowired
    RuleNameRepository ruleNameRepository;
    
    @Autowired
    IRuleNameService ruleNameService;
    
    @Test
    @Order(1)
    public void getAllRuleServiceTest() {
	// WHEN
	List<RuleName> listRuleName = ruleNameService.getAllRuleName();
	// THEN
	assertEquals(listRuleName.isEmpty(), false);
    }
    
    @Test
    @Order(2)
    public void addNewRuleServiceTest() {
	// GIVEN
	boolean result;
	List<RuleName> listResult = ruleNameRepository.findAll();
	int sizeBeforeSave = listResult.size();
	RuleName ruleName = new RuleName();
	ruleName.setName("Name test");
	ruleName.setDescription("Description Test");
	// WHEN
	result = ruleNameService.addRuleName(ruleName);
	listResult = ruleNameRepository.findAll();
	// THEN
	assertEquals(result, true);
	assertEquals(listResult.size(), sizeBeforeSave + 1);
    }
    
    @Test
    @Order(3)
    public void addNewRuleServiceErrorTest() {
	// GIVEN
	boolean result;
	List<RuleName> listResult = ruleNameRepository.findAll();
	int sizeBeforeSave = listResult.size();
	RuleName ruleName = new RuleName();
	ruleName.setName("Name test");
	ruleName.setDescription("");
	// WHEN
	result = ruleNameService.addRuleName(ruleName);
	listResult = ruleNameRepository.findAll();
	// THEN
	assertEquals(result, false);
	assertEquals(listResult.size(), sizeBeforeSave);
    }
    
    @Test
    @Order(4)
    public void updateRuleServiceTest() {
	// GIVEN
	boolean result;
	List<RuleName> listResult = ruleNameRepository.findAll();
	int numberOfCurve = listResult.size();
	int index = numberOfCurve - 1;
	RuleName ruleNameUpdate = new RuleName();
	ruleNameUpdate = listResult.get(index);
	int id = ruleNameUpdate.getId();
	ruleNameUpdate.setName("Test Name Update");
	// WHEN
	result = ruleNameService.updateRuleName(id, ruleNameUpdate);
	Optional<RuleName> ruleName = ruleNameRepository.findById(id);
	RuleName ruleNameAfterUpdate = ruleName.get();
	String name = ruleNameAfterUpdate.getName();
	// THEN
	assertEquals(result, true);
	assertEquals(name, "Test Name Update");
    }
    
    @Test
    @Order(5)
    public void updateRuleServiceErrorTest() {
	// GIVEN
	boolean result;
	List<RuleName> listResult = ruleNameRepository.findAll();
	int numberOfCurve = listResult.size();
	int index = numberOfCurve - 1;
	RuleName ruleNameUpdate = new RuleName();
	ruleNameUpdate = listResult.get(index);
	int id = ruleNameUpdate.getId();
	ruleNameUpdate.setName("");
	// WHEN
	result = ruleNameService.updateRuleName(id, ruleNameUpdate);
	Optional<RuleName> ruleName = ruleNameRepository.findById(id);
	RuleName ruleNameAfterUpdate = ruleName.get();
	String name = ruleNameAfterUpdate.getName();
	// THEN
	assertEquals(result, false);
	assertEquals(name, "Test Name Update");
    }
    
    @Test
    @Order(6)
    public void deleteRuleServiceTest() {
	// GIVEN
	boolean result;
	List<RuleName> listResult = ruleNameRepository.findAll();
	int numberOfRule = listResult.size();
	int index = numberOfRule - 1;
	RuleName ruleNameDelete = new RuleName();
	ruleNameDelete = listResult.get(index);
	int id = ruleNameDelete.getId();
	// WHEN
	result = ruleNameService.deleteRuleName(id);
	Optional<RuleName> ruleName = ruleNameRepository.findById(id);
	// THEN
	assertEquals(result, true);
	assertEquals(ruleName.isPresent(), false);
    }
    
    @Test
    @Order(7)
    public void deleteRuleServiceErrorTest() {
	// GIVEN
	boolean result;
	int falseId = 3;
	// WHEN
	result = ruleNameService.deleteRuleName(falseId);
	// THEN
	assertEquals(result, false);
    }
    
    @Test
    @Order(8)
    public void getRuleByIdServiceTest() {
	//GIVEN
	RuleName ruleName = new RuleName();
	//WHEN
	ruleName = ruleNameService.getRuleNameById(1);
	//THEN
	assertEquals(ruleName.getId(), 1);
	assertEquals(ruleName.getName(), "Rule");
    }
    
    @Test
    @Order(9)
    public void getRuleByIdServiceErrorTest() {
	//GIVEN
	RuleName ruleName = new RuleName();
	//WHEN
	ruleName = ruleNameService.getRuleNameById(3);
	//THEN
	assertFalse(ruleName.getId() > 0);
	assertEquals(ruleName.getName(), null);
    }

}
