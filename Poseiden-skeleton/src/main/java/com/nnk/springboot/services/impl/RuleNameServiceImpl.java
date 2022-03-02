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

/**
 * La classe RuleNameServiceImpl est la classe d'implémentation de l'interface
 * IRuleNameService.
 * 
 * @see IRuleNameService
 * @author Dylan
 *
 */
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    @Autowired
    RuleNameRepository ruleNameRepository;

    private static Logger logger = LogManager.getLogger(RuleNameServiceImpl.class);

    @Override
    public List<RuleName> getAllRuleName() {
	/*
	 * On instancie une liste qui va récupérer les RuleName en base de données.
	 */
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
	/*
	 * On vérifie que les informations du RuleName entrés dans le formulaire HTML
	 * soient bien présentes, si tel est le cas on instancie un nouvel objet
	 * RuleName afin de lui donner les informations récupérées et de le sauvegarder
	 * en base de données, tout en indiquant au boolean de renvoyer true pour
	 * confirmer que la sauvegarde est effective, si des données sont manquantes
	 * lors de la vérification des informations alors le boolean reste false.
	 * 
	 */
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
	/*
	 * On instancie un RuleName optional afin de vérifier qu'il existe bien un
	 * RuleName sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<RuleName> searchRuleName;
	searchRuleName = ruleNameRepository.findById(id);
	RuleName ruleNameUpdate = new RuleName();
	/*
	 * On vérifie qu'un RuleName est bien présent en base de donnée, ensuite
	 * nous vérifions que les informations du RuleName mis à jour soient bien
	 * présentes et conformes, si tout ceci est correct nous sauvegardons les
	 * modifications des informations du RuleName et nous passons le boolean
	 * en true afin d'indiquer que la mis à jour est validée, si une condition
	 * est non remplie alors le boolean reste sur false afin d'indiquer que
	 * la mis à jour n'est pas validée.
	 */
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
	/*
	 * On instancie un RuleName optional afin de vérifier qu'il existe bien un
	 * RuleName sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<RuleName> searchRuleName;
	searchRuleName = ruleNameRepository.findById(id);
	/*
	 * On vérifie qu'un RuleName est bien présent avec l'id indiqué, si tel
	 * est le cas nous supprimons celui-ci et indiquons true au boolean afin
	 * d'indiquer que la suppression est validée, si aucun RuleName n'est
	 * trouvé alors nous laissons le boolean sur false afin d'indiquer que
	 * la suppression n'est pas validée. 
	 */
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
	/*
	 * On instancie un RuleName optional afin de vérifier qu'il existe bien un
	 * RuleName sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<RuleName> searchRuleName;
	searchRuleName = ruleNameRepository.findById(id);
	RuleName ruleName = new RuleName();
	/*
	 * On vérifie qu'un RuleName est bien présent avec l'id indiqué, si tel
	 * est le cas nous récupérons les informations de celui-ci dans un
	 * nouvel objet RuleName, sinon le RuleName retourné reste à null.
	 */
	if (searchRuleName.isPresent()) {
	    ruleName = searchRuleName.get();
	    logger.info("The ruleName with id number " + id + " successfully recovered");
	} else {
	    logger.error("No ruleName found with id number " + id);
	}
	return ruleName;
    }

}
