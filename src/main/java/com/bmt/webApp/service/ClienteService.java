package com.bmt.webApp.service;

import com.bmt.webApp.dto.ClienteDto;
import com.bmt.webApp.model.Cliente;

public interface ClienteService {

    Iterable<Cliente> buscarTodos();
    
    void CreateClient(ClienteDto clienteDto);

    void clientEdit(Long id);

}
