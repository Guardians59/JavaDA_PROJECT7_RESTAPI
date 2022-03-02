package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

/**
 * L'interface ICurvePointService est le service qui permets de gérer les éléments
 * CRUD de l'entité CurvePoint.
 * @author Dylan
 *
 */
public interface ICurvePointService {
    /**
     * La méthode getAllCurvePoint nous permets de récupérer les CurvePoint enregistrés
     * en base de données.
     * @return List des CurvePoint enregistrés.
     */
    public List<CurvePoint> getAllCurvePoint();
    /**
     * La méthode addCurvePoint nous permets de sauvegarder un CurvePoint en base 
     * de données.
     * @param newCurvePoint le nouveau CurvePoint récupérer depuis le formulaire
     * HTML.
     * @return boolean true si l'ajout est validé, false si une erreur est rencontrée.
     */
    public boolean addCurvePoint(CurvePoint newCurvePoint);
    /**
     * La méthode updateCurvePoint nous permets de mettre à jour un CurvePoint.
     * @param id l'id du CurvePoint séléctionnné.
     * @param curvePoint les nouvelles informations du CurvePoint récupérées dans 
     * le formulaire HTML.
     * @return boolean true si la mis à jour est validée, false si une erreur est
     * rencontrée.
     */
    public boolean updateCurvePoint(int id, CurvePoint curvePoint);
    /**
     * La méthode deleteCurvePoint nous permets de supprimer un CurvePoint.
     * @param id l'id du CurvePoint à supprimer.
     * @return boolean true si la suppression est validée, false si une erreur est
     * rencontrée.
     */
    public boolean deleteCurvePoint(int id);
    /**
     * La méthode getCurvePointById nous permets de récupérer un CurvePoint via son
     * id.
     * @param id l'id du CurvePoint que l'on souhaite récupérer.
     * @return CurvePoint le CurvePoint récupéré.
     */
    public CurvePoint getCurvePointById(int id);

}
