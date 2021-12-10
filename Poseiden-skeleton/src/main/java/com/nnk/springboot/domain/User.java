package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username")
    private String username;

    /*@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
	    message = "Le mot de passe doit contenir un minimum de 8 caractères, dont un chiffre, une majuscule et un symbole")
   */ @Column(name = "password")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    private String fullname;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(Integer id, @NotBlank(message = "Username is mandatory") String username, String password,
	    @NotBlank(message = "FullName is mandatory") String fullname, Role role) {
	super();
	this.id = id;
	this.username = username;
	this.password = password;
	this.fullname = fullname;
	this.role = role;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getFullname() {
	return fullname;
    }

    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

    public Role getRole() {
	return role;
    }

    public void setRole(Role role) {
	this.role = role;
    }

}
