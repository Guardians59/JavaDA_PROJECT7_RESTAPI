package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;

public interface IUserService {

    public List<User> getAllUser();

    public boolean addUser(User newUser);

    public boolean updateUser(int id, User user);

    public boolean deleteUser(int id);

    public User getUserById(int id);

}
