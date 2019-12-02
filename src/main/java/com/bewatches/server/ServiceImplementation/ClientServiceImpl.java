package com.bewatches.server.ServiceImplementation;

import com.bewatches.server.Model.App.Watch;
import com.bewatches.server.Model.Client;
import com.bewatches.server.Model.Role;
import com.bewatches.server.Repository.ClientRepository;
import com.bewatches.server.Repository.RoleRepository;
import com.bewatches.server.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;


    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client registration(Client client) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> clientRoles = new ArrayList<>();
        clientRoles.add(roleUser);

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRoles(clientRoles);

        Client registeredClient = clientRepository.save(client);

        return registeredClient;
    }

    @Override
    public List<Client> getAll() {
        List<Client> result = clientRepository.findAll();
        return result;
    }

    @Override
    public Client findByLogin(String login) {
        Client client = clientRepository.findByLogin(login);
        return client;
    }

    @Override
    public Client findById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return client;
    }

    @Override
    public Client findByEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        return client;
    }

    @Override
    public Client findByWatch(Watch watch){
        Client client = clientRepository.findByWatches(watch);
        return client;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
