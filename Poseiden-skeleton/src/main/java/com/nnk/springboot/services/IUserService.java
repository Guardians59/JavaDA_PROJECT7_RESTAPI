package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;
/**
 * L'interface IUserService est le service qui nous permets de gérer les éléments
 * CRUD de l'entité User.
 * @author Dylan
 *
 */
public interface IUserService {
    /**
     * La méthode getAllUser nous permets de récupérer tous les User enregistrés
     * en base de données.
     * @return List des User enregistrés.
     */
    public List<User> getAllUser();
    /**
     * La méthode addUser nous permets d'enregistrer un User en base de données.
     * @param newUser le nouveau User récupéré depuis le formulaire HTML.
     * @return boolean true si l'enregistrement est validé, false si une erreur
     * est rencontrée.
     */
    public boolean addUser(User newUser);
    /**
     * La méthode updateUser nous permets de mettre à jour un User en base de
     * données.
     * @param id l'id du User séléctionné.
     * @param user les nouvelles informations du User récupérées depuis le
     * formulaire HTML.
     * @return boolean true si la mis à jour est validée, false si une erreur est
     * rencontrée.
     */
    public boolean updateUser(int id, User user);
    /**
     * La méthode deleteUser nous permets de supprimer un User.
     * @param id l'id du User à supprimer.
     * @return boolean true si la suppression est validée, false si une erreur
     * est rencontrée.
     */
    public boolean deleteUser(int id);
    /**
     * La méthode getUserById nous permets de récupérer un User via son Id.
     * @param id l'id de l'User que l'on souhaite récupérer.
     * @return User l'User récupéré.
     */
    public User getUserById(int id);

}
