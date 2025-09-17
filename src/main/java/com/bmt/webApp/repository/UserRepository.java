package com.bmt.webApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
    
    // Método para encontrar um usuário pelo email
    Usuario findByEmail(String email);

    // Verifica se o email existe para um usuário diferente do ID fornecido
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(String email, Long id);
}
