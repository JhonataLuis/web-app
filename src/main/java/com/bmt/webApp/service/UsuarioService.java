package com.bmt.webApp.service;

import com.bmt.webApp.model.Usuario;

public interface  UsuarioService {

    Iterable<Usuario> listUsers();

    Usuario createUser(Usuario users);

    Usuario updateUser(Usuario users);

    Usuario findByEmail(String email);

    Usuario findById(Long id);

}
