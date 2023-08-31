package com.example.demo;

import com.example.demo.entitys.ClientEntity;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client clientDto) {
        ClientEntity entity = ClientMapper.dtoToEntity(clientDto);
        ClientEntity savedEntity = clientRepository.save(entity).block();
        return ClientMapper.entityToDto(savedEntity);
    }

    public List<Client> getAllClients() {
        List<ClientEntity> entities = clientRepository.findAll().collectList().block();
        return entities.stream().map(ClientMapper::entityToDto).collect(Collectors.toList());
    }

    public Client getClientById(String id) {
        if (!ObjectId.isValid(id)) {
            return null; // Aquí puedes decidir qué hacer. Devolver null, lanzar una excepción, etc.
        }
        ObjectId objectId = new ObjectId(id);
        ClientEntity entity = clientRepository.findById(objectId.toString()).block();
        if (entity == null) {
            return null; // O manejar de la forma que prefieras si el cliente no se encuentra.
        }
        return ClientMapper.entityToDto(entity);
    }


    public List<Client> bulkRetrieveClients(List<String> ids) {
        List<ObjectId> objectIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
        List<ClientEntity> entities = clientRepository.findAllByGivenIds(objectIds).collectList().block();
        return entities.stream().map(ClientMapper::entityToDto).collect(Collectors.toList());
    }


}