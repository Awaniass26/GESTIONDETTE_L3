package com.ism.repository.list;
import java.util.List;
import java.util.Optional;

import com.ism.entity.User;
import com.ism.repository.UserRepository;
public class UserRepositoryList extends RepositoryListImpl<User> implements UserRepository {

    private List<User> users;

    public UserRepositoryList(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean loginExist(String login) {
        return users.stream().anyMatch(user -> user.getLogin().equals(login));

    }

    @Override
    public User seConnecter(String login, String password) {
         Optional<User> userOptional = users.stream()
                                           .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                                           .findFirst();
        return userOptional.orElse(null);
    }

}
