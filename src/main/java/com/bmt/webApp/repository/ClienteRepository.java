package com.bmt.webApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.model.Cliente;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    public Cliente findByEmail(String email);
}
