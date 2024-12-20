package com.ism.services.impl;

import com.ism.entity.User;
import com.ism.repository.bd.UserRepositoryBd;
import com.ism.services.UserService;

public class UserServiceImpl extends ServiceImpl<User> implements UserService {

    public UserServiceImpl(UserRepositoryBd repository) {
        super(repository);
    }

}
