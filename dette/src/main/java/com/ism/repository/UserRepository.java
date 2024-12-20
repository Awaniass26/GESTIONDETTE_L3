package com.ism.repository;

import com.ism.core.repository.Repository;
import com.ism.entity.User;

public interface UserRepository extends Repository<User> {

    public boolean loginExist(String login);
    User seConnecter(String login, String password);
}
