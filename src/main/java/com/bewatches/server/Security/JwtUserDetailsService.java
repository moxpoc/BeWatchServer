package com.bewatches.server.Security;

import com.bewatches.server.Model.Client;
import com.bewatches.server.Security.jwt.JwtClient;
import com.bewatches.server.Security.jwt.JwtClientFactory;
import com.bewatches.server.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final ClientService clientService;

    @Autowired
    public JwtUserDetailsService(ClientService clientService){
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Client client = clientService.findByLogin(username);

        if(client == null){
            throw new UsernameNotFoundException("User with login: " + username + " not found");
        }

        JwtClient jwtClient = JwtClientFactory.create(client);
        return jwtClient;
    }

}
