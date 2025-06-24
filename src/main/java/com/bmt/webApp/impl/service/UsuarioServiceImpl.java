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

    /**
     * Lista todos os usuários.
     * 
     * @return Uma lista de todos os usuários.
     */
    @Override
    public Iterable<Usuario> listUsers() {
        
        logger.info("Listando todos os usuários {}.");
        // Retorna todos os usuários do repositório
        return userRepository.findAll();
    }

    /**
     * Cria um novo usuário.
     * 
     * @param users O usuário a ser criado.
     * @return O usuário criado.
     * @throws IllegalArgumentException Se o usuário já existir com o email fornecido.
     */
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

    /**
     * Atualiza um usuário existente.
     * 
     * @param users O usuário a ser atualizado.
     * @return O usuário atualizado.
     * @throws IllegalArgumentException Se o usuário não for encontrado com o ID fornecido.
     */
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

    /**
     * Busca um usuário pelo email.
     * @param email O email do usuário a ser buscado.
     * @return O usuário encontrado.
     * 
     */
    @Override
    public Usuario findByEmail(String email) {
        logger.info("Buscando usuário pelo email: {}", email);
        // Busca o usuário pelo email no repositório
        return userRepository.findByEmail(email);
    }

    /**
     * Busca um usuário pelo ID.    
     * 
     * @param id O ID do usuário a ser buscado.
     * @return O usuário encontrado.
     * @throws IllegalArgumentException Se o usuário não for encontrado com o ID fornecido.
     */
    @Override
    public Usuario findById(Long id) {
        logger.info("Buscando usuário pelo ID: {}", id);
        // Busca o usuário pelo ID no repositório
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    /**
     * Deleta um usuário pelo ID.
     * 
     * @param id O ID do usuário a ser deletado.
     * @throws IllegalArgumentException Se o usuário não for encontrado com o ID fornecido.
     */
    @Override
    public void deleteUser(Long id) {
        logger.info("Tentando deletar o usuário com ID: {}", id);
        
        // Verificando se o usuário existe
        if(!userRepository.existsById(id)) {
            logger.warn("Usuário com ID {} não encontrado.", id);
            throw new IllegalArgumentException("Usuário não encontrado com o ID: " + id);
        }
        
        // Deletando o usuário do repositório
        userRepository.deleteById(id);
        logger.info("Usuário com ID {} deletado com sucesso.", id);
    }

}
