package com.example.Project_server.performances;

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
public class PerformancesServiceImpTest {
    @Autowired
    private PerformancesServiceImp performancesServiceImp;
    @MockBean
    private PerformancesRepo performancesRepo;

    @Test
    public void readAll() {
        List<Performances> expected = new ArrayList<>();
        Mockito.when(performancesRepo.findAll()).thenReturn(expected);
        List<Performances> performances = performancesServiceImp.readAll();
        Assertions.assertEquals(expected, performances);
    }

    @Test
    public void create() {
        Performances performances = new Performances();
        Performances expected = new Performances();
        expected.setId(1L);
        Mockito.when(performancesRepo.save(performances)).thenReturn(expected);
        Performances actual = performancesServiceImp.create(performances);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void read() {
        Performances performances = new Performances();
        performances.setId(1L);
        Optional<Performances> expected = Optional.of(performances);
        Mockito.when(performancesRepo.findById(1L)).thenReturn(expected);
        Assertions.assertEquals(performances, performancesServiceImp.read(1L));
    }

    @Test
    public void update() {
        Performances performances = new Performances();
        performances.setId(1L);
        Optional<Performances> expected = Optional.of(performances);
        Mockito.when(performancesRepo.findById(1L)).thenReturn(expected);
        Assertions.assertTrue(performancesServiceImp.update(performances, 1L));
    }

    @Test
    public void delete() {
        Performances performances = new Performances();
        performances.setId(1L);
        Optional<Performances> expected = Optional.of(performances);
        Mockito.when(performancesRepo.findById(1L)).thenReturn(expected);
        Assertions.assertTrue(performancesServiceImp.delete(1L));
    }
}
