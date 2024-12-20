package com.ism.repository.list;

import com.ism.entity.Client;
import com.ism.repository.ClientRepository;

public class ClientRepositoryList extends RepositoryListImpl<Client> implements ClientRepository {

    @Override
    public Client readClientBySurname(String surname) {
        for (Client cl : datas) {
            if (cl.getSurname().equalsIgnoreCase(surname)) {
                return cl;
            }
        }
        return null;
    }

    @Override
    public Client readClientByTelephone(String telephone) {
        for (Client cl : datas) {
            if (cl.getTelephone().equals(telephone)) {
                return cl;
            }
        }
        return null;
    }

    @Override
    public Client findById(int id) {
        for (Client cl : datas) {
            if (cl.getId() == id) {
                return cl;
            }
        }
        return null;
    }
}
