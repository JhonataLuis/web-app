package com.bmt.webApp.impl.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bmt.webApp.dto.ClienteDto;
import com.bmt.webApp.model.Cliente;
import com.bmt.webApp.repository.ClienteRepository;
import com.bmt.webApp.service.ClienteService;

import jakarta.persistence.EntityNotFoundException;



@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clientRepository;

     /**
     * Lista todos os Clientes
     * 
     * @return Os Clientes cadastrados no sistema
     */

    @Override
    public Iterable<Cliente> buscarTodos() {
        var clients = clientRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
       return clients;
    }

    @Override
    public void CreateClient(ClienteDto clientDto) {
        
        Cliente client = new Cliente();
        if(client != null){
            client.setFirstName(clientDto.getFirstName());
            client.setLastName(clientDto.getLastName());
            client.setEmail(clientDto.getEmail());
            client.setPhone(clientDto.getPhone());
            client.setAddress(clientDto.getAddress());
            client.setStatus(clientDto.getStatus());
            client.setCreatedAt(new Date());

            clientRepository.save(client);
        }
    }

    @Override
    public void clientUpdate(ClienteDto dto) {
       Cliente client = clientRepository.findById(dto.getId())
       .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());
        client.setStatus(dto.getStatus());

        clientRepository.save(client);

    }

    @Override
    public void clientDelete(Long id){
        Cliente client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrada ou usuário não autorizado"));

        clientRepository.delete(client);
    }

}
