package com.bmt.webApp.impl.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bmt.webApp.dto.ClienteDto;
import com.bmt.webApp.model.Cliente;
import com.bmt.webApp.repository.ClienteRepository;
import com.bmt.webApp.service.ClienteService;



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
    public void clientUpdate(Long id) {
       Cliente client = clientRepository.findById(id).orElse(null);

       if(client != null){

        //update client details
        ClienteDto clientDto = new ClienteDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());
        clientDto.setPhone(client.getPhone());
        clientDto.setStatus(client.getStatus());

        clientRepository.save(client);

       }

    }

}
