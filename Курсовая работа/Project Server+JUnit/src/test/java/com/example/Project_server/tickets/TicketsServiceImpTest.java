package com.example.Project_server.tickets;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketsServiceImpTest {

    @Autowired
    private TicketsServiceImp ticketsServiceImp;
    @MockBean
    private TicketsRepo ticketsRepo;

    @Test
    public void create() {
        Tickets tickets = new Tickets();
        Tickets expected = new Tickets();
        expected.setId(1L);
        Mockito.when(ticketsRepo.save(tickets)).thenReturn(expected);
        Tickets actual = ticketsServiceImp.create(tickets);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void readAll() {
        List<Tickets> expected = new ArrayList<>();
        Mockito.when(ticketsRepo.findAll()).thenReturn(expected);
        List<Tickets> tickets = ticketsServiceImp.readAll();
        Assertions.assertEquals(expected, tickets);
    }

    @Test
    public void read() {
        Tickets tickets = new Tickets();
        tickets.setId(1L);
        Optional<Tickets> expected = Optional.of(tickets);
        Mockito.when(ticketsRepo.findById(1L)).thenReturn(expected);
        Assertions.assertEquals(tickets, ticketsServiceImp.read(1L));
    }

    @Test
    public void update() {
        Tickets tickets = new Tickets();
        tickets.setId(1L);
        Optional<Tickets> expected = Optional.of(tickets);
        Mockito.when(ticketsRepo.findById(1L)).thenReturn(expected);
        Assertions.assertTrue(ticketsServiceImp.update(tickets, 1L));
    }

    @Test
    public void delete() {
        Tickets tickets = new Tickets();
        tickets.setId(1L);
        Optional<Tickets> expected = Optional.of(tickets);
        Mockito.when(ticketsRepo.findById(1L)).thenReturn(expected);
        Assertions.assertTrue(ticketsServiceImp.delete(1L));
    }
}
