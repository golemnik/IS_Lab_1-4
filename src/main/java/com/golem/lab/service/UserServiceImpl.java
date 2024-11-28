package com.golem.lab.service;

import java.util.HashSet;
import java.util.List;

import com.golem.lab.model.Client;
import com.golem.lab.repository.RoleRepository;
import com.golem.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(Client client) {
//        client.setPassword(passwordEncoder.encode(client.getPassword()));
//        client.setRoles(new HashSet<>(roleRepository.findAll()));
        client.setPassword("{noop}" + client.getPassword());
        userRepository.save(client);
    }

    @Override
    public Client findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Client> findAllClients() {
        return userRepository.findAll();
    }
}
