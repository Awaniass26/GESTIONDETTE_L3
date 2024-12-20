package com.ism.repository;

import com.ism.entity.Client;
import com.ism.core.repository.Repository;

public interface ClientRepository extends Repository<Client> {
    public Client readClientBySurname(String surname);
    public Client readClientByTelephone(String telephone);
    public Client findById(int id);
}
