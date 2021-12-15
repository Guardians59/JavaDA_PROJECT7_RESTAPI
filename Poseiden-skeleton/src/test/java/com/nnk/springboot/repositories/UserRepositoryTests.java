package com.nnk.springboot.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Test
    @Order(1)
    public void getAllUserTest() {
	//WHEN
	List<User> listUser = userRepository.findAll();
	//THEN
	assertTrue(listUser.size() > 0);
    }
    
    @Test
    @Order(2)
    public void saveUserTest() {
	//GIVEN
	User user = new User();
	user.setFullname("User Test Repo");
	user.setUsername("user test repo");
	user.setPassword(passwordEncoder.encode("Azerty1&"));
	user.setRole(Role.ROLE_USER);
	List<User> listUser = userRepository.findAll();
	int numberOfUser = listUser.size();
	//WHEN
	userRepository.save(user);
	listUser = userRepository.findAll();
	int numberOfUserAfterSave = listUser.size();
	//WHEN
	assertEquals(numberOfUserAfterSave, numberOfUser + 1);
    }
    
    @Test
    @Order(3)
    public void updateUserTest() {
	//GIVEN
	List<User> listUser = userRepository.findAll();
	int numberOfUser = listUser.size();
	int index = numberOfUser - 1;
	User userUpdate = new User();
	userUpdate = listUser.get(index);
	userUpdate.setUsername("repo user test");
	//WHEN
	userRepository.save(userUpdate);
	listUser = userRepository.findAll();
	String userName = listUser.get(index).getUsername();
	//THEN
	assertEquals(numberOfUser, listUser.size());
	assertEquals(userName, "repo user test");
    }
    
    @Test
    @Order(4)
    public void deleteUserTest() {
	//GIVEN
	User userDelete = new User();
	List<User> listUser = userRepository.findAll();
	int numberOfUser = listUser.size();
	int index = numberOfUser - 1;
	userDelete = listUser.get(index);
	//WHEN
	userRepository.delete(userDelete);
	listUser = userRepository.findAll();
	Optional<User> user = userRepository.findById(userDelete.getId());
	//THEN
	assertEquals(listUser.size(), numberOfUser - 1);
	assertEquals(user.isPresent(), false);
    }
}
