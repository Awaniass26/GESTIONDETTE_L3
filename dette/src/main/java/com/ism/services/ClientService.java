package com.ism.services;

import com.ism.entity.Client;
import com.ism.entity.User;

public interface ClientService extends Service<Client> {
    public Client surnameExist(String surname);

    Client telephoneExist(String telephone);

    public Client findById(int id);

     void createClientWithAccount(Client client, User user); 
    

}
