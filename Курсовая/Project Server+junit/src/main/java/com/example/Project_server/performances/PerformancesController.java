package com.example.Project_server.performances;


import com.example.Project_server.tickets.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PerformancesController {

    private final PerformancesService performancesService;
    private final TicketsService ticketsService;

    @Autowired
    public PerformancesController(PerformancesService performancesService, TicketsService ticketsService){
        this.performancesService = performancesService;
        this.ticketsService = ticketsService;
    }




    @GetMapping(value = "/performances")
    public ResponseEntity<List<Performances>> readPerformances() {
        final List<Performances> performances = performancesService.readAll();

        return performances != null && performances.isEmpty()
                ? new ResponseEntity<>(performances, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/performances/{id}")
    public ResponseEntity<Performances> readPerformance(@PathVariable(name = "id") int id) {
        final Performances performance = performancesService.read(id);

        return performance != null
                ? new ResponseEntity<>(performance, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PutMapping(value = "/performances/{id}")
    public ResponseEntity<?> updatePerformance(@RequestBody Performances performance, @PathVariable(name = "id") int id) {
        if (performancesService.update(performance, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/performances/{id}")
    public ResponseEntity<?> deletePerformance(@PathVariable(name = "id") int id) {
        if (performancesService.read(id) != null) {
            ticketsService.deleteByPerformanceId(id);
            performancesService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/performances")
    public Performances createNewPerformance(@RequestBody Performances performance) {
        return performancesService.create(performance);
    }

}
