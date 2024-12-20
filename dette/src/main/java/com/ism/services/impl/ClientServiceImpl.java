package com.ism.services.impl;

import com.ism.entity.Client;
import com.ism.entity.User;
import com.ism.repository.bd.ClientRepositoryBd;
import com.ism.services.ClientService;

public class ClientServiceImpl extends ServiceImpl<Client> implements ClientService {

    public ClientServiceImpl(ClientRepositoryBd repository) {
        super(repository);
    }
 
    @Override
    public Client surnameExist(String surname) {
        return ((ClientRepositoryBd) repository).readClientBySurname(surname);
    }

    @Override
    public Client telephoneExist(String telephone) {
        return ((ClientRepositoryBd) repository).readClientByTelephone(telephone);
    }

    @Override
    public Client findById(int id) {
        return ((ClientRepositoryBd) repository).findById(id); 

    }


     public void createClientWithAccount(Client client, User user) {
        ((ClientRepositoryBd) repository).createClientWithAccount(client, user);
    }

   

}
