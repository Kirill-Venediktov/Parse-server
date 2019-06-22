package com.example.Parseserver.service;

import com.example.Parseserver.entity.Client;
import com.example.Parseserver.entity.Goods;
import com.example.Parseserver.entity.Role;
import com.example.Parseserver.repository.ClientRepository;
import com.example.Parseserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

@Service("clientService")
public class ClientService {
    private ClientRepository clientRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired

    public ClientService(ClientRepository clientRepository,
                         RoleRepository roleRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Client findCurrentClient(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        return clientRepository.findByLogin(auth.getName());
    }


    public Client findClientByLogin(String login){
        return clientRepository.findByLogin(login);
    }

    public void saveClient(Client client) {
            client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        client.setActive(1);
//        client.setLinks(new ArrayList<String>());
//        client.setGoods(new CopyOnWriteArrayList<Goods>());
        Role clientRole = roleRepository.findByRole("ADMIN");
        client.setRoles(new HashSet<Role>(Arrays.asList(clientRole)));
        clientRepository.save(client);

    }


}
