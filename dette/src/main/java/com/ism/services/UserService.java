package com.ism.services;

import java.util.List;

import com.ism.entity.User;

public interface UserService {
    public void create(User user);

    public List<User> get();

}
