package com.nnk.springboot.repositories;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.RuleName;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RuleNameRepositoryTests {
    
    @Autowired
    RuleNameRepository ruleNameRepository;
    
    @Test
    @Order(1)
    public void getListAllRuleTest() {
	//WHEN
	List<RuleName> listRuleName = ruleNameRepository.findAll();
	//THEN
	assertTrue(listRuleName.size() > 0);
    }
    
    @Test
    @Order(2)
    public void saveRuleTest() {
	//GIVEN
	RuleName ruleName = new RuleName();
	ruleName.setName("Name Test");
	ruleName.setDescription("Description Test");
	List<RuleName> listRuleName = ruleNameRepository.findAll();
	int numberOfRule = listRuleName.size();
	//WHEN
	ruleNameRepository.save(ruleName);
	listRuleName = ruleNameRepository.findAll();
	int numberOfRuleAfterSave = listRuleName.size();
	//THEN
	assertEquals(numberOfRuleAfterSave, numberOfRule + 1);
    }
    
    @Test
    @Order(3)
    public void updateRuleTest() {
	//GIVEN
	List<RuleName> listRuleName = ruleNameRepository.findAll();
	int numberOfRule = listRuleName.size();
	int index = numberOfRule - 1;
	RuleName ruleNameUpdate = new RuleName();
	ruleNameUpdate = listRuleName.get(index);
	ruleNameUpdate.setName("Test Name Update");
	//WHEN
	ruleNameRepository.save(ruleNameUpdate);
	listRuleName = ruleNameRepository.findAll();
	String name = listRuleName.get(index).getName();
	//THEN
	assertEquals(numberOfRule, listRuleName.size());
	assertEquals(name, "Test Name Update");
    }
    
    @Test
    @Order(4)
    public void deleteRuleTest() {
	//GIVEN
	RuleName ruleNameDelete = new RuleName();
	List<RuleName> listRuleName = ruleNameRepository.findAll();
	int size = listRuleName.size();
	int index = size - 1;
	ruleNameDelete = listRuleName.get(index);
	//WHEN
	ruleNameRepository.delete(ruleNameDelete);
	listRuleName = ruleNameRepository.findAll();
	Optional<RuleName> ruleName = ruleNameRepository.findById(ruleNameDelete.getId());
	//THEN
	assertEquals(listRuleName.size(), size - 1);
	assertEquals(ruleName.isPresent(), false);
    }

}
