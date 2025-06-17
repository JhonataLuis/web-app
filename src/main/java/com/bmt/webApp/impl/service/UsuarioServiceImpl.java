package com.bmt.webApp.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bmt.webApp.model.Usuario;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService{

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<Usuario> listUsers() {
        
        throw new UnsupportedOperationException("Unimplemented method 'listUsers'");
    }

    @Override
    public void createUser() {
       logger.info("Tentando criar um novo usuário: {}");
       Usuario user = new Usuario();
       userRepository.save(user);
       logger.info("Usuario salvo com o ID: {}", user.getId());
        
    }

}
