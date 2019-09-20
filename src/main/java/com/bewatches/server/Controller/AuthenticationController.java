package com.bewatches.server.Controller;

import com.bewatches.server.Dto.AuthRequestDto;
import com.bewatches.server.Model.Client;
import com.bewatches.server.Security.jwt.JwtTokenProvider;
import com.bewatches.server.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final ClientService clientService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, ClientService clientService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.clientService = clientService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody AuthRequestDto authRequestDto){
        try{
            String login = authRequestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authRequestDto.getPassword()));
            Client client = clientService.findByLogin(login);
            if (client == null){
                throw new UsernameNotFoundException("User with login: " + login + " not found");
            }
            String token = jwtTokenProvider.createToken(login, client.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid login or password");
        }
    }


}
