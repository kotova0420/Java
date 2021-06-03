package com.example.Project_server.clients;

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
public class ClientsServiceImpTest {
    @Autowired
    private ClientsServiceImp clientsServiceImp;
    @MockBean
    private ClientsRepo clientsRepo;

    @Test
    public void create() {
        Clients clients = new Clients();
        Clients expected = new Clients();
        expected.setId(1L);
        Mockito.when(clientsRepo.save(clients)).thenReturn(expected);
        Clients actual = clientsServiceImp.create(clients);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void readAll() {
        List<Clients> expected = new ArrayList<>();
        Mockito.when(clientsRepo.findAll()).thenReturn(expected);
        List<Clients> clients = clientsServiceImp.readAll();
        Assertions.assertEquals(expected, clients);
    }

    @Test
    public void read() {
        Clients clients = new Clients();
        clients.setId(1L);
        Optional<Clients> expected = Optional.of(clients);
        Mockito.when(clientsRepo.findById(1L)).thenReturn(expected);
        Assertions.assertEquals(clients, clientsServiceImp.read(1L));
    }

    @Test
    public void update() {
        Clients clients = new Clients();
        clients.setId(1L);
        Optional<Clients> expected = Optional.of(clients);
        Mockito.when(clientsRepo.findById(1L)).thenReturn(expected);
        Assertions.assertTrue(clientsServiceImp.update(clients, 1L));
    }

    @Test
    public void delete() {
        Clients clients = new Clients();
        clients.setId(1L);
        Optional<Clients> expected = Optional.of(clients);
        Mockito.when(clientsRepo.findById(1L)).thenReturn(expected);
        Assertions.assertTrue(clientsServiceImp.delete(1L));
    }

}
