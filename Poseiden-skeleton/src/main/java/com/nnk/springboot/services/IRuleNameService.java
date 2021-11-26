package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

public interface IRuleNameService {

    public List<RuleName> getAllRuleName();

    public boolean addRuleName(RuleName newRuleName);

    public boolean updateRuleName(int id, RuleName ruleName);

    public boolean deleteRuleName(int id);

    public RuleName getRuleNameById(int id);

}
