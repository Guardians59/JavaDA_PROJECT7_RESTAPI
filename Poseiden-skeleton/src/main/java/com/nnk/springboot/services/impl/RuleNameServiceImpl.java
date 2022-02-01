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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.IRuleNameService;

@Service
public class RuleNameServiceImpl implements IRuleNameService {

    @Autowired
    RuleNameRepository ruleNameRepository;

    private static Logger logger = LogManager.getLogger(RuleNameServiceImpl.class);

    @Override
    public List<RuleName> getAllRuleName() {
	List<RuleName> listRuleName = new ArrayList<>();
	listRuleName = ruleNameRepository.findAll();
	if (listRuleName.isEmpty()) {
	    logger.info("The list of ruleName is empty");
	} else {
	    logger.info("List of ruleName successfully recovered");
	}
	return listRuleName;
    }

    @Override
    @Transactional
    public boolean addRuleName(RuleName newRuleName) {
	boolean result = false;
	if (!newRuleName.getName().isEmpty() && !newRuleName.getDescription().isEmpty()) {

	    RuleName ruleName = new RuleName();
	    ruleName.setName(newRuleName.getName());
	    ruleName.setDescription(newRuleName.getDescription());
	    ruleNameRepository.save(ruleName);
	    result = true;
	    logger.info("The new ruleName is registered successfully");
	} else {
	    logger.error("A data in the form is missing");
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateRuleName(int id, RuleName ruleName) {
	boolean result = false;
	Optional<RuleName> searchRuleName;
	searchRuleName = ruleNameRepository.findById(id);
	RuleName ruleNameUpdate = new RuleName();

	if (searchRuleName.isPresent()) {
	    if (!ruleName.getName().isEmpty() && !ruleName.getDescription().isEmpty()) {
		ruleNameUpdate = searchRuleName.get();
		ruleNameUpdate.setName(ruleName.getName());
		ruleNameUpdate.setDescription(ruleName.getDescription());
		ruleNameRepository.save(ruleNameUpdate);
		result = true;
		logger.info("Updated ruleName with id number " + id + " successfully");
	    } else {
		logger.error("A data in the form is missing");
	    }

	} else {
	    logger.error("No ruleName found with id number " + id);
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteRuleName(int id) {
	boolean result = false;
	Optional<RuleName> searchRuleName;
	searchRuleName = ruleNameRepository.findById(id);
	
	if (searchRuleName.isPresent()) {
	    ruleNameRepository.deleteById(id);
	    result = true;
	    logger.info("The ruleName with id number " + id + " has been deleted correctly");
	} else {
	    logger.error("No ruleName found with id number " + id);
	}
	return result;
    }

    @Override
    public RuleName getRuleNameById(int id) {
	Optional<RuleName> searchRuleName;
	searchRuleName = ruleNameRepository.findById(id);
	RuleName ruleName = new RuleName();
	
	if (searchRuleName.isPresent()) {
	    ruleName = searchRuleName.get();
	    logger.info("The ruleName with id number " + id + " successfully recovered");
	} else {
	    logger.error("No ruleName found with id number " + id);
	}
	return ruleName;
    }

}
