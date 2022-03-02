package com.nnk.springboot.services;
/**
 * L'interface IPasswordValidService est le service qui permet de vérifier que
 * le mot de passe est valide, en respectant certaines conditions.
 * @author Dylan
 *
 */
public interface IPasswordValidService {
    /**
     * La méthde isPasswordValid nous permets de vérifier que le mot de passe
     * est valide, en respectant certaines conditions.
     * @param password le mot de passe récupérer dans le formulaire HTML.
     * @return boolean true si le mot de passe est valide, false si il ne remplit
     * pas les conditions.
     */
    public boolean isPasswordValid(String password);

}
