package com.example.Project_server.shares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SharesController {

    //1 переменная класса SharesService где прописаны методы джавы
    private final SharesService sharesService;

    @Autowired
    public SharesController(SharesService sharesService) {
        this.sharesService = sharesService;
    }


    //определяю метод readShares, отвечает за get запрос, использует readAll
    @GetMapping(value = "/shares")
    public ResponseEntity<List<Shares>> readShares() {

        final List<Shares> shares = sharesService.readAll();

        return shares != null && !shares.isEmpty()
                ? new ResponseEntity<>(shares, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/shares/{id}")
    public ResponseEntity<Shares> readShare(@PathVariable(name = "id") int id) {

        final Shares share = sharesService.read(id);

        return share != null
                ? new ResponseEntity<>(share, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/shares/{id}")
    public ResponseEntity<?> updateShare(@RequestBody Shares share, @PathVariable(name = "id") int id) {

        if (sharesService.update(share, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/shares/{id}")
    public ResponseEntity<?> deleteShare(@PathVariable(name = "id") int id) {

        if (sharesService.read(id) != null) {
            sharesService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/shares")
    public Shares createNewShare(@RequestBody Shares share) {
        return sharesService.create(share);
    }

}
