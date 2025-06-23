package com.bmt.webApp.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Usuario;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
   
    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<Usuario> listUsers() {
        
        logger.info("Listando todos os usuários {}.");
        // Retorna todos os usuários do repositório
        return userRepository.findAll();
    }

    @Override
    public Usuario createUser(Usuario users) {
       logger.info("Tentando criar um novo usuário: {}");

         // Verificando se o usuário já existe pelo email
         if(userRepository.findByEmail(users.getEmail()) != null) {
             logger.warn("Usuário com email {} já existe.", users.getEmail());
             throw new IllegalArgumentException("Usuário já existe com o email: " + users.getEmail());
         }
         // Criptografando a senha do usuário antes de salvar
         String senhaCriptografada = passwordEncoder.encode(users.getPassword());
         users.setPassword(senhaCriptografada);
         logger.info("Senha criptografada: {}", senhaCriptografada);
        // Salvando o usuário no repositório
       return userRepository.save(users);
        
    }

    @Override
    public Usuario updateUser(Usuario users) {
        logger.info("Tentando atualizar o usuário: {}", users.getEmail());
        
        // Verificando se o usuário existe
        if(userRepository.findById(users.getId()) == null) {
            logger.warn("Usuário com ID {} não encontrado.", users.getId());
            throw new IllegalArgumentException("Usuário não encontrado com o ID: " + users.getId());
        }       
        // Criptografando a senha do usuário antes de atualizar
        String senhaCriptografada = passwordEncoder.encode(users.getPassword());
        users.setPassword(senhaCriptografada);
        logger.info("Senha criptografada: {}", senhaCriptografada);
        // Atualizando o usuário no repositório
        return userRepository.save(users);  

    }

    @Override
    public Usuario findByEmail(String email) {
        logger.info("Buscando usuário pelo email: {}", email);
        // Busca o usuário pelo email no repositório
        return userRepository.findByEmail(email);
    }

    @Override
    public Usuario findById(Long id) {
        logger.info("Buscando usuário pelo ID: {}", id);
        // Busca o usuário pelo ID no repositório
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

}
