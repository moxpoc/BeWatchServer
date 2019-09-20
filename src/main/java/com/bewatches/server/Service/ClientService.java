package com.bewatches.server.Service;

import com.bewatches.server.Model.Client;

import java.util.List;

public interface ClientService {
    Client registration(Client client);

    List<Client> getAll();

    Client findByLogin(String login);

    Client findById(Long id);

    Client findByEmail(String email);

    void delete(Long id);
}
