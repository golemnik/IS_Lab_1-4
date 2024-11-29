
package com.golem.lab.service;


import com.golem.lab.model.Client;

import java.util.List;


public interface UserService {
    void save(Client client);

    Client findByUsername(String username);
    List<Client> findAllClients();
}
