package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;
/**
 * L'interface IRuleNameService est le service qui permets de gérer les éléments
 * CRUD de l'entité RuleName.
 * @author Dylan
 *
 */
public interface IRuleNameService {
    /**
     * La méthode getAllRuleName nous permets de récupérer tous les RuleName
     * enregistrés en base de données.
     * @return List des RuleName enregistrés.
     */
    public List<RuleName> getAllRuleName();
    /**
     * La méthode addRuleName nous permets d'enregistrer un RuleName en base de 
     * données.
     * @param newRuleName le nouveau RuleName récupéré depuis le formulaire HTML.
     * @return boolean true si l'enregistrement est validé, false si une erreur
     * est rencontrée.
     */
    public boolean addRuleName(RuleName newRuleName);
    /**
     * La méthode updateRuleName nous permets de mettre à jour un RuleName.
     * @param id l'id du RuleName sélectionné.
     * @param ruleName les nouvelles informations du RuleName récupéré depuis le
     * formulaire HTML.
     * @return boolean true si la mis à jour est validée, false si une erreur
     * est rencontrée.
     */
    public boolean updateRuleName(int id, RuleName ruleName);
    /**
     * La méthode deleteRuleName nous permets de supprimer un RuleName.
     * @param id l'id du RuleName à supprimer.
     * @return boolean true si la suppression est validée, false si une erreur est
     * rencontrée.
     */
    public boolean deleteRuleName(int id);
    /**
     * La méthode getRuleNameById nous permets de récupérer un RuleName via son Id.
     * @param id l'id du RuleName que l'on souhaite récupérer.
     * @return RuleName le RuleName récupéré.
     */
    public RuleName getRuleNameById(int id);

}
