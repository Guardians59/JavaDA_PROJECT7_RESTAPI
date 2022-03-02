package com.nnk.springboot.services;

import java.security.Principal;

import com.nnk.springboot.domain.LoggedUsername;
/**
 * L'interface IOAuthService est le service qui nous permets de récupérer le 
 * nom d'utilisateur de l'user connécté.
 * @author Dylan
 *
 */
public interface IOAuthService {
    /**
     * La méthode getUsername nous permets de récupérer le nom d'utilisateur du
     * user connecté via la base de données.
     * @param user les informations de l'utilisateur connecté.
     * @return LoggedUsername afin de pouvoir utiliser l'objet pour afficher
     * l'username via Thymeleaf.
     */
    public LoggedUsername getUsername(Principal user);
    /**
     * La méthode getOauthUsername nous permets de récupérer le login du user
     * connecté via github.
     * @param user les informations de l'utilisateur connecté.
     * @return LoggedUsername afin de pouvoir utiliser l'objet pour afficher
     * l'username via Thymeleaf.
     */
    public LoggedUsername getOauthUsername(Principal user);

}
