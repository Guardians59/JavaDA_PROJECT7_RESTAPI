package com.nnk.springboot.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements Serializable, UserDetails{
    /**
     * 
     */
    private static final long serialVersionUID = 8230681370658600779L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
	    message = "The password must contain a minimum of 8 characters, including a number, a capital letter and a symbol")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    private String fullname;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(Integer id, @NotBlank(message = "Username is mandatory") String username,
	    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Le mot de passe doit contenir un minimum de 8 caractères, dont un chiffre, une majuscule et un symbole") String password,
	    @NotBlank(message = "FullName is mandatory") String fullname, Role role) {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	list.add(new SimpleGrantedAuthority(role.getAuthority()));
	return list;
    }

    @Override
    public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
    }

}
