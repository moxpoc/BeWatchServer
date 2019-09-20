package com.bewatches.server.Security.jwt;

import com.bewatches.server.Model.Client;
import com.bewatches.server.Model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class  JwtClientFactory {

    public JwtClientFactory(){

    }

    public static JwtClient create(Client client){
        return new JwtClient(
                client.getId(),
                client.getLogin(),
                client.getEmail(),
                client.getPassword(),
                mapTograntedAutorities(new ArrayList<>(client.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapTograntedAutorities(List<Role> clientRoles){
        return clientRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
