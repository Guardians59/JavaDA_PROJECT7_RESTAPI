package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

/**
 * L'interface IBidListService est le service qui nous permets de gérer les éléments CRUD
 * de l'entité BidList.
 * @author Dylan
 *
 */
public interface IBidListService {
    /**
     * La méthode getAllBidList nous permets de récupérer tous les BidList enregistrés
     * en base de données.
     * @return List des BidList enregistrés.
     */
    public List<BidList> getAllBidList();
    /**
     * La méthode addBidList nous permets de sauvegarder un BidList en base de données.
     * @param newBidList le nouveau BidList récupéré depuis le formulaire HTML.
     * @return boolean true si l'ajout du BidList est confirmé, false si une erreur
     * est rencontrée.
     */
    public boolean addBidList (BidList newBidList);
    /**
     * La méthode updateBidList nous permets de mettre à jour un BidList.
     * @param id l'id du BidList séléctionné.
     * @param bidList les nouvelles informations du BidList récupéré dans le
     * formulaire HTML.
     * @return boolean true si la mis à jour est confirmé, false si une erreur
     * est rencontrée.
     */
    public boolean updateBidList (int id, BidList bidList);
    /**
     * La méthode deleteBidList nous permets de supprimer un BidList.
     * @param id l'id du BidList à supprimer.
     * @return boolean true si la suppression est confirmée, false si une erreur
     * rencontrée.
     */
    public boolean deleteBidList (int id);
    /**
     * La méthode getBidById nous permets de récupérer un BidList via son id.
     * @param id l'id du BidList que l'on souhaite récupérer.
     * @return BidList le BidList récupéré.
     */
    public BidList getBidById (int id);

}
