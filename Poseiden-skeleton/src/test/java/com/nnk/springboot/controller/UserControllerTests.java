package com.nnk.springboot.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.impl.UserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de la vue de la liste user")
    public void getUserListPageTest() throws Exception {
	//GIVEN
	List<User> list = new ArrayList<>();
	User user = new User();
	user.setId(20);
	user.setFullname("User test mock");
	user.setPassword("Azerty123&");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	list.add(user);
	//WHEN
	when(userService.getAllUser()).thenReturn(list);
	mockMvc.perform(get("/user/list"))
	//THEN
	.andExpect(view().name("user/list"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de la vue d'ajout user")
    public void getUserAddPageTest() throws Exception {
	//WHEN
	mockMvc.perform(get("/user/add"))
	//THEN
	.andExpect(view().name("user/add"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de l'ajout user")
    public void addUserPageTest() throws Exception {
	//GIVEN
	User user = new User();
	user.setId(20);
	user.setFullname("User test mock");
	user.setPassword("Azerty123&");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	//WHEN
	when(userService.addUser(user)).thenReturn(true);
	mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("user", user))
	//THEN
		.andExpect(view().name("user/list"))
		.andExpect(model().attributeExists("success"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de l'erreur lors de l'ajout user")
    public void addErrorUserPageTest() throws Exception {
	//GIVEN
	User user = new User();
	user.setId(20);
	user.setFullname("User test mock");
	user.setPassword("Azerty123&");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	//WHEN
	when(userService.addUser(user)).thenReturn(false);
	mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("user", user))
	//THEN
		.andExpect(view().name("user/add"))
		.andExpect(model().attributeExists("error"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de l'erreur binding lors de l'ajout user")
    public void addBindingErrorUserPageTest() throws Exception {
	//GIVEN
	User user = new User();
	user.setId(20);
	user.setFullname("User test mock");
	user.setPassword("");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	//WHEN
	when(userService.addUser(user)).thenReturn(false);
	mockMvc.perform(post("/user/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("user", user))
	//THEN
		.andExpect(view().name("user/add"))
		.andExpect(model().attributeDoesNotExist("success"))
		.andExpect(model().attributeDoesNotExist("error"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de la vue de mis à jour user")
    public void getUserEditPageTest() throws Exception {
	//GIVEN
	User user = new User();
	user.setId(21);
	user.setFullname("User test mock");
	user.setPassword("Azerty21&");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	//WHEN
	when(userService.getUserById(21)).thenReturn(user);
	mockMvc.perform(get("/user/edit/21"))
	//THEN
	.andExpect(view().name("user/update"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de la mis à jour user")
    public void updateUserPageTest() throws Exception {
	//GIVEN
	User user = new User();
	user.setId(21);
	user.setFullname("User test mock");
	user.setPassword("Azerty21&");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	//WHEN
	when(userService.updateUser(21, user)).thenReturn(true);
	mockMvc.perform(post("/user/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("user", user))
	//THEN
		.andExpect(view().name("user/list"))
		.andExpect(model().attributeExists("updateSuccess"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de l'erreur lors de la mis à jour user")
    public void updateErrorUserPageTest() throws Exception {
	//GIVEN
	User oldUser = new User();
	oldUser.setId(21);
	oldUser.setFullname("User test mock");
	oldUser.setPassword("Azerty21&");
	oldUser.setUsername("User mock");
	oldUser.setRole(Role.ROLE_USER);
	User user = new User();
	user.setId(21);
	user.setFullname("User test mock");
	user.setPassword("Azerty21&");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	//WHEN
	when(userService.getUserById(21)).thenReturn(oldUser);
	when(userService.updateUser(21, user)).thenReturn(false);
	mockMvc.perform(post("/user/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("user", user))
	//THEN
		.andExpect(view().name("user/update"))
		.andExpect(model().attributeExists("updateError"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de l'erreur binding lors de la mis à jour user")
    public void updateBindingErrorUserPageTest() throws Exception {
	//GIVEN
	User oldUser = new User();
	oldUser.setId(21);
	oldUser.setFullname("User test mock");
	oldUser.setPassword("Azerty21&");
	oldUser.setUsername("User mock");
	oldUser.setRole(Role.ROLE_USER);
	User user = new User();
	user.setId(21);
	user.setFullname("User test mock");
	user.setPassword("");
	user.setUsername("User mock");
	user.setRole(Role.ROLE_USER);
	//WHEN
	when(userService.getUserById(21)).thenReturn(oldUser);
	when(userService.updateUser(21, user)).thenReturn(false);
	mockMvc.perform(post("/user/update/21").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
		.flashAttr("user", user))
	//THEN
		.andExpect(view().name("user/update"))
		.andExpect(model().attributeDoesNotExist("updateSuccess"))
		.andExpect(model().attributeDoesNotExist("updateError"))
		.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de la suppression user")
    public void deleteUserPageTest() throws Exception {
	//WHEN
	when(userService.deleteUser(22)).thenReturn(true);
	mockMvc.perform(get("/user/delete/22"))
	//THEN
	.andExpect(view().name("user/list"))
	.andExpect(model().attributeExists("deleteSuccess"))
	.andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser("user test")
    @DisplayName("Test de l'erreur lors de la suppression user")
    public void deleteErrorUserPageTest() throws Exception {
	//WHEN
	when(userService.deleteUser(22)).thenReturn(false);
	mockMvc.perform(get("/user/delete/22"))
	//THEN
	.andExpect(view().name("user/list"))
	.andExpect(model().attributeExists("deleteError"))
	.andExpect(status().isOk());
    }

}
