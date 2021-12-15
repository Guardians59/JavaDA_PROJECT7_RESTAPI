package com.nnk.springboot.services;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    public void getAllUserServiceTest() {
	// WHEN
	List<User> listUser = userService.getAllUser();
	// THEN
	assertEquals(listUser.isEmpty(), false);
    }

    @Test
    @Order(2)
    public void addNewUserServiceTest() {
	// GIVEN
	boolean result;
	List<User> listUser = userRepository.findAll();
	int sizeBeforeSave = listUser.size();
	User user = new User();
	user.setFullname("Fullname User");
	user.setUsername("Username user");
	user.setPassword(passwordEncoder.encode("Azerty2&"));
	user.setRole(Role.ROLE_USER);
	// WHEN
	result = userService.addUser(user);
	listUser = userRepository.findAll();
	// THEN
	assertEquals(result, true);
	assertEquals(listUser.size(), sizeBeforeSave + 1);
    }

    @Test
    @Order(3)
    public void addNewUserServiceErrorTest() {
	// GIVEN
	boolean result;
	List<User> listUser = userRepository.findAll();
	int sizeBeforeSave = listUser.size();
	User user = new User();
	user.setFullname("Fullname User");
	user.setUsername("");
	user.setPassword(passwordEncoder.encode("Azerty2&"));
	user.setRole(Role.ROLE_USER);
	// WHEN
	result = userService.addUser(user);
	listUser = userRepository.findAll();
	// THEN
	assertEquals(result, false);
	assertEquals(listUser.size(), sizeBeforeSave);
    }

    @Test
    @Order(4)
    public void updateUserServiceTest() {
	// GIVEN
	boolean result;
	List<User> listUser = userRepository.findAll();
	int numberOfUser = listUser.size();
	int index = numberOfUser - 1;
	User userUpdate = new User();
	userUpdate = listUser.get(index);
	userUpdate.setUsername("Update username");
	int id = userUpdate.getId();
	// WHEN
	result = userService.updateUser(id, userUpdate);
	listUser = userRepository.findAll();
	String userName = listUser.get(index).getUsername();
	// THEN
	assertEquals(result, true);
	assertEquals(userName, "Update username");
    }

    @Test
    @Order(5)
    public void updateUserServiceErrorTest() {
	// GIVEN
	boolean result;
	List<User> listUser = userRepository.findAll();
	int numberOfUser = listUser.size();
	int index = numberOfUser - 1;
	User userUpdate = new User();
	userUpdate = listUser.get(index);
	userUpdate.setUsername("Update username error");
	userUpdate.setFullname("");
	int id = userUpdate.getId();
	// WHEN
	result = userService.updateUser(id, userUpdate);
	listUser = userRepository.findAll();
	String userName = listUser.get(index).getUsername();
	// THEN
	assertEquals(result, false);
	assertEquals(userName, "Update username");
    }
    
    @Test
    @Order(6)
    public void deleteUserServiceTest() {
	//GIVEN
	boolean result;
	List<User> listUser = userRepository.findAll();
	int numberOfUser = listUser.size();
	int index = numberOfUser - 1;
	User userDelete = new User();
	userDelete = listUser.get(index);
	int id = userDelete.getId();
	//WHEN
	result = userService.deleteUser(id);
	Optional<User> user = userRepository.findById(id);
	//THEN
	assertEquals(result, true);
	assertEquals(user.isPresent(), false);
    }
    
    @Test
    @Order(7)
    public void deleteUserServiceErrorTest() {
	//GIVEN
	boolean result;
	List<User> listUser = userRepository.findAll();
	int falseId = 4;
	int numberOfUser = listUser.size();
	//WHEN
	result = userService.deleteUser(falseId);
	listUser = userRepository.findAll();
	//THEN
	assertEquals(result, false);
	assertEquals(listUser.size(), numberOfUser);
    }
    
    @Test
    @Order(8)
    public void getUserByIdServiceTest() {
	//GIVEN
	User user = new User();
	//WHEN
	user = userService.getUserById(1);
	//THEN
	assertEquals(user.getFullname(), "Administrator");
	assertEquals(user.getRole(), Role.ROLE_ADMIN);
    }
    
    @Test
    @Order(9)
    public void getUserByIdServiceErrorTest() {
	//GIVEN
	User user = new User();
	//WHEN
	user = userService.getUserById(5);
	//THEN
	assertEquals(user.getFullname(), null);
    }
}
