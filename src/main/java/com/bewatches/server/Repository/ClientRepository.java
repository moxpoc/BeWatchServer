package com.bewatches.server.Repository;

import com.bewatches.server.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByLogin(String login);
    Client findByEmail(String email);
}
