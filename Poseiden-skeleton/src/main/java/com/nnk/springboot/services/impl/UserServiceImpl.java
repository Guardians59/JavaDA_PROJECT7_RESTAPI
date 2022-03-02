package com.nnk.springboot.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.IPasswordValidService;
import com.nnk.springboot.services.IUserService;

/**
 * La classe UserServiceImpl est l'implémentation de l'interface IUserService.
 * 
 * @see IUserService
 * @author Dylan
 *
 */
@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IPasswordValidService passwordValid;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public List<User> getAllUser() {
	/*
	 * On instancie une liste qui va récupérer les User en base de données.
	 */
	List<User> listUser = new ArrayList<>();
	listUser = userRepository.findAll();
	if (listUser.isEmpty()) {
	    logger.info("The list of user is empty");
	} else {
	    logger.info("List of user successfully recovered");
	}
	return listUser;
    }

    @Override
    @Transactional
    public boolean addUser(User newUser) {
	boolean result = false;
	/*
	 * On instancie un boolean qui permet de vérifier que le mot de passe remplit
	 * les conditions attendues avec le service PasswordValid.
	 */
	boolean passwordIsValid = passwordValid.isPasswordValid(newUser.getPassword());
	/*
	 * On vérifie que les informations du User entrés dans le formulaire HTML
	 * soient bien présentes et que le mot de passe est valide, si tel est le
	 * cas on instancie un nouvel objet User afin de lui donner les 
	 * informations récupérées, nous codons également le mot de passe afin qu'il
	 * ne soit pas visible dans la base données, et nous sauvegardons le User, tout
	 * en indiquant au boolean de renvoyer true pour confirmer que la sauvegarde
	 * est effective, si des données sont manquantes lors de la vérification
	 * des informations alors le boolean reste false.
	 * 
	 */
	if (!newUser.getUsername().isEmpty() && !newUser.getFullname().isEmpty() 
		&& passwordIsValid == true) {

	    User user = new User();
	    user.setUsername(newUser.getUsername());
	    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
	    user.setFullname(newUser.getFullname());
	    user.setRole(newUser.getRole());
	    userRepository.save(user);
	    result = true;
	    logger.info("The new user is registered successfully");
	} else {
	    logger.error("A data in the form is missing");
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateUser(int id, User user) {
	boolean result = false;
	/*
	 * On instancie un User optional afin de vérifier qu'il existe bien un
	 * User sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<User> searchUser = userRepository.findById(id);
	/*
	 * On instancie un boolean qui permet de vérifier que le mot de passe remplit
	 * les conditions attendues avec le service PasswordValid.
	 */
	boolean passwordIsValid = passwordValid.isPasswordValid(user.getPassword());
	User userUpdate = new User();
	/*
	 * On vérifie qu'un User est bien présent en base de donnée, ensuite
	 * nous vérifions que les informations du User mis à jour soient bien
	 * présentes et conformes, ainsi que le mot de passe soit valide, si tout
	 * ceci est correct nous sauvegardons les modifications des informations
	 * du User, tout en codant le mot de passe afin qu'il ne soit pas visible
	 * en base de données, et nous passons le boolean en true afin d'indiquer
	 * que la mis à jour est validée, si une condition est non remplie alors le
	 * boolean reste sur false afin d'indiquer que la mis à jour n'est pas validée.
	 */
	if (searchUser.isPresent()) {
	    if (!user.getUsername().isEmpty() && !user.getFullname().isEmpty() 
		    && passwordIsValid == true) {

		userUpdate = searchUser.get();
		userUpdate.setUsername(user.getUsername());
		userUpdate.setFullname(user.getFullname());
		userUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
		userUpdate.setRole(user.getRole());
		userRepository.save(userUpdate);
		result = true;
		logger.info("Updated user with id number " + id + " successfully");
	    } else {
		logger.error("A data in the form is missing");
	    }
	} else {
	    logger.error("No user found with id number " + id);
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean deleteUser(int id) {
	boolean result = false;
	/*
	 * On instancie un User optional afin de vérifier qu'il existe bien un
	 * User sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<User> searchUser = userRepository.findById(id);
	/*
	 * On vérifie qu'un User est bien présent avec l'id indiqué, si tel
	 * est le cas nous supprimons celui-ci et indiquons true au boolean afin
	 * d'indiquer que la suppression est validée, si aucun User n'est
	 * trouvé alors nous laissons le boolean sur false afin d'indiquer que
	 * la suppression n'est pas validée. 
	 */
	if (searchUser.isPresent()) {
	    userRepository.deleteById(id);
	    result = true;
	    logger.info("The user with id number " + id + " has been deleted correctly");
	} else {
	    logger.error("No user found with id number " + id);
	}
	return result;
    }

    @Override
    public User getUserById(int id) {
	/*
	 * On instancie un User optional afin de vérifier qu'il existe bien un
	 * User sauvegarder en base de données avec l'id entrée en paramètre.
	 */
	Optional<User> searchUser = userRepository.findById(id);
	User user = new User();
	/*
	 * On vérifie qu'un User est bien présent avec l'id indiqué, si tel
	 * est le cas nous récupérons les informations de celui-ci dans un
	 * nouvel objet User, sinon le User retourné reste à null.
	 */
	if (searchUser.isPresent()) {
	    user = searchUser.get();
	    logger.info("The user with id number " + id + " successfully recovered");
	} else {
	    logger.error("No user found with id number " + id);
	}
	return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userRepository.findByUsername(username);
	return user;
    }

}
