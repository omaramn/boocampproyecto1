package com.example.demo;

import com.example.demo.api.ClientsApiDelegate;
import com.example.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
@Component
public class apidelegateimplement implements ClientsApiDelegate {

    @Autowired
    private ClientService clientService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ClientsApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> createClient(Client client) {
        Client createdClient = null;
        if (createdClient != null) {
            return ResponseEntity.created(URI.create("/clients/" + createdClient.getId())).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @Override
    public ResponseEntity<Client> getClientById(String clientId) {
        Client client = clientService.getClientById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build(); // Respuesta 404 si el cliente no se encuentra o si el ID no es válido.
        }
        return ResponseEntity.ok(client); // Respuesta 200 con el cliente encontrado.
    }

    @Override
    public ResponseEntity<List<Client>> bulkRetrieveClients(List<String> ids) {
        List<Client> clients = clientService.bulkRetrieveClients(ids);
        if (clients != null && !clients.isEmpty()) {
            return ResponseEntity.ok(clients);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
