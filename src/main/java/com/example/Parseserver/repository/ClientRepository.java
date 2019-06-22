package com.example.Parseserver.repository;

import com.example.Parseserver.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//сменим CrudRepository на JpaRepository
@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByLogin(String login);

}
