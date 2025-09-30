package com.bmt.webApp.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bmt.webApp.dto.UsuarioDto;
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
    public Usuario createUser(Usuario user) {
       logger.info("Tentando criar um novo usuário: {}");

         // Verificando se o usuário já existe pelo email
         if(userRepository.findByEmail(user.getEmail()) != null) {
             logger.warn("Usuário com email {} já existe.", user.getEmail());
             throw new IllegalArgumentException("Usuário já existe com o email: " + user.getEmail());
         }
         
         // Criptografando a senha do usuário antes de salvar
         String senhaCriptografada = passwordEncoder.encode(user.getPassword());
         user.setPassword(senhaCriptografada);
         logger.info("Senha criptografada: {}", senhaCriptografada);
        // Salvando o usuário no repositório
       return userRepository.save(user);
        
    }

    /**
     * Atualiza um usuário existente.
     * 
     * @param users O usuário a ser atualizado.
     * @return O usuário atualizado.
     * @throws IllegalArgumentException Se o usuário não for encontrado com o ID fornecido.
     */
    @Override
    public Usuario updateUser(UsuarioDto userDto) {
        logger.info("Tentando atualizar o usuário: {}", userDto.getEmail());

        Usuario existingUser = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + userDto.getId()));  
        
        //verifica se o email já está em uso por outro usuário
        if(!existingUser.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmailAndIdNot(userDto.getEmail(), userDto.getId())) {
            logger.warn("Email {} já está em uso por outro usuário.", userDto.getEmail());
            throw new IllegalArgumentException("Email já está em uso por outro usuário: " + userDto.getEmail());
        }
        

        // Atualizando os dados do usuário
        
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail()); 
        existingUser.setFuncao(userDto.getFuncao());
        existingUser.setDataAtualizacao(userDto.getDataAtualizacao());

        //Atualiza a senha somente se foi fornecida uma nova senha(não vazia)
        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()){
            String senhaCriptografada = passwordEncoder.encode(userDto.getPassword());
            existingUser.setPassword(senhaCriptografada);
            logger.info("Senha criptografada: {}", senhaCriptografada);
        } else {
            // Mantém a senha atual se nenhuma nova senha for fornecida
            existingUser = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + userDto.getId()));
            existingUser.setPassword(existingUser.getPassword());

        }

        System.out.println("Atualizando usuário: " + existingUser.getId());
        System.out.println("Email atual: " + existingUser.getEmail());
        System.out.println("Novo email: " + userDto.getEmail());
        System.out.println("Email existe? " + userRepository.findByEmail(userDto.getEmail()));


        return userRepository.save(existingUser);

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

    /**
     * Conta o total de membros da equipe.
     * 
     * @return O total de membros.
     */
    @Override
    public long countMembers(){
        return userRepository.count();
    }

}
