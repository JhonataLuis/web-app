package com.bmt.webApp.service;

import com.bmt.webApp.model.Usuario;

public interface  UsuarioService {

    Iterable<Usuario> listUsers();

    void createUser();

}
