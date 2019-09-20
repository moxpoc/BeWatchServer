package com.bewatches.server.Controller;

import com.bewatches.server.Model.Client;
import com.bewatches.server.Repository.RoleRepository;
import com.bewatches.server.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    ClientService clientService;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Client saveClient(@RequestBody Client client){
        Client existClient = clientService.findByEmail(client.getEmail());
        if(existClient==null){
            return clientService.registration(client);
        } else{
            throw new UsernameNotFoundException("User already exist");

        }


    }
}
