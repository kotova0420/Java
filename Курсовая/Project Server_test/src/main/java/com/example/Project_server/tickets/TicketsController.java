package com.example.Project_server.tickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketsController {

    private final TicketsService ticketsService;

    @Autowired
    public TicketsController(TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }


    @GetMapping(value = "/tickets")
    public ResponseEntity<List<Tickets>> readTickets() {
        final List<Tickets> tickets = ticketsService.readAll();

        return tickets != null && !tickets.isEmpty()
                ? new ResponseEntity<>(tickets, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/tickets/{id}")
    public ResponseEntity<Tickets> readTicket(@PathVariable(name = "id") int id) {
        final Tickets ticket = ticketsService.read(id);

        return ticket != null
                ? new ResponseEntity<>(ticket, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/tickets/{id}")
    public ResponseEntity<?> updateTicket(@RequestBody Tickets ticket, @PathVariable(name = "id") int id) {
        if (ticketsService.update(ticket, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/tickets/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable(name = "id") int id) {
        if (ticketsService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/tickets")
    public Tickets createNewTicket(@RequestBody Tickets ticket) {
        return ticketsService.create(ticket);
    }

}
