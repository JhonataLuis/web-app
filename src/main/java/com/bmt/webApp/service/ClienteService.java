package com.bmt.webApp.service;

import com.bmt.webApp.model.Cliente;
import com.bmt.webApp.model.ClienteDto;

public interface ClienteService {

    Iterable<Cliente> buscarTodos();
    
    void CreateClient(ClienteDto clienteDto);

    void clientEdit(Long id);

}
