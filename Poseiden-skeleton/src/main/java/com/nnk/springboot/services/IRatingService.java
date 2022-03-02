package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;
/**
 * L'interface IRatingService est le service qui permets de gérer les éléments
 * CRUD de l'entité Rating.
 * @author Dylan
 *
 */
public interface IRatingService {
    /**
     * La méthode getAllRating nous permets de récupérer tous les Rating enregistrés
     * en base de données.
     * @return List des Rating enregistrés.
     */
    public List<Rating> getAllRating();
    /**
     * La méthode addRating nous permets d'enregistrer un Rating en base de donnée.
     * @param newRating le nouveau Rating récupéré depuis le formulaire HTML.
     * @return boolean true si l'enregistrement est validé, false si une erreur
     * est rencontrée.
     */
    public boolean addRating(Rating newRating);
    /**
     * La méthode updateRating nous permets de mettre à jour un Rating.
     * @param id l'id du Rating sélectionné.
     * @param rating les nouvelles informations du Rating récupéré dans le
     * formulaire HTML.
     * @return boolean true si la mis à jour est validée, false si une erreur est
     * rencontrée.
     */
    public boolean updateRating(int id, Rating rating);
    /**
     * La méthode deleteRating nous permets de supprimer un Rating.
     * @param id l'id du Rating à supprimer.
     * @return boolean true si la suppression est validée, false si une erreur
     * est rencontrée.
     */
    public boolean deleteRating(int id);
    /**
     * La méthode getRatingById nous permets de récupérer un Rating via son Id.
     * @param id l'id du Rating que l'on souhaite récupérer.
     * @return Rating le Rating récupérer.
     */
    public Rating getRatingById(int id);

}
