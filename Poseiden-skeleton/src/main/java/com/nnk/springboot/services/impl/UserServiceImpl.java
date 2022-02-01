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
	boolean passwordIsValid = passwordValid.isPasswordValid(newUser.getPassword());
	if (!newUser.getUsername().isEmpty() && !newUser.getFullname().isEmpty() && passwordIsValid == true) {

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
	Optional<User> searchUser = userRepository.findById(id);
	boolean passwordIsValid = passwordValid.isPasswordValid(user.getPassword());
	User userUpdate = new User();

	if (searchUser.isPresent()) {
	    if (!user.getUsername().isEmpty() && !user.getFullname().isEmpty() && passwordIsValid == true) {

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
	Optional<User> searchUser = userRepository.findById(id);

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
	Optional<User> searchUser = userRepository.findById(id);
	User user = new User();
	
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
