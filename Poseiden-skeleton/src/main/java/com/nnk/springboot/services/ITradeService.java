package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;
/**
 * L'interface ITradeService est le service qui nous permets de gérer les éléments
 * CRUD de l'entité Trade.
 * @author Dylan
 *
 */
public interface ITradeService {
    /**
     * La méthode getAllTrade nous permets de récupérer tous les Trade enregistrés
     * en base de données. 
     * @return List des Trade enregistrés.
     */
    public List<Trade> getAllTrade();
    /**
     * La méthode addTrade nous permets d'enregistrer un Trade en base de données.
     * @param newTrade le nouveau Trade récupéré depuis le formulaire HTML.
     * @return boolean true si l'enregistrement est validé, false si une erreur
     * est rencontrée.
     */
    public boolean addTrade(Trade newTrade);
    /**
     * La méthode updateTrade nous permets de mettre à jour un Trade.
     * @param id l'id du Trade séléctionné.
     * @param trade les nouvelles informations du Trade récupérées depuis le
     * formulaire HTML.
     * @return boolean true si la mis à jour est validée, false si une erreur
     * est rencontrée.
     */
    public boolean updateTrade(int id, Trade trade);
    /**
     * La méthode deleteTrade nous permets de supprimer un Trade.
     * @param id l'id du Trade à supprimer.
     * @return boolean true si la suppression est validée, false si une erreur
     * est rencontrée.
     */
    public boolean deleteTrade(int id);
    /**
     * La méthode getTradeById nous permets de récupérer un Trade via son Id.
     * @param id l'id du Trade que l'on souhaite récupérer.
     * @return Trade le Trade récupéré.
     */
    public Trade getTradeById(int id);

}
