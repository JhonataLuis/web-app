package com.bmt.webApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
    
    // Método para encontrar um usuário pelo email
    Usuario findByEmail(String email);
}
