package com.example.Project_server.clients;

import com.example.Project_server.tickets.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientsController {

    //1 переменные классов сервиса(в них только create, delete, update)
    private final ClientsService clientsService;
    private final TicketsService ticketsService;

    // 2 как init
    @Autowired
    public ClientsController(ClientsService clientsService, TicketsService ticketsService) {
        this.clientsService = clientsService;
        this.ticketsService = ticketsService;
    }




    @GetMapping(value = "/clients")
    public ResponseEntity<List<Clients>> readClients() {
        final List<Clients> clients = clientsService.readAll();

        return clients != null && !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Clients> readClient(@PathVariable(name = "id") int id) {
        final Clients client = clientsService.read(id);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<?> updateClient(@RequestBody Clients client, @PathVariable(name = "id") int id) {
        if (clientsService.update(client, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(name = "id") int id) {
        if (clientsService.read(id) != null) {
            ticketsService.deleteByClientId(id);
            clientsService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/clients")
    public Clients createNewClient(@RequestBody Clients client) {
        return clientsService.create(client);
    }

}
