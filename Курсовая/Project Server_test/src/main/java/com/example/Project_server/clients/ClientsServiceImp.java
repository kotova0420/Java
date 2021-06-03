package com.example.Project_server.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsServiceImp implements ClientsService{

    @Autowired
    private ClientsRepo clientsRepo;

    @Override
    public Clients create(Clients client) {
        return clientsRepo.save(client);
    }

    @Override
    public List<Clients> readAll() {
        return clientsRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Clients read(long id) {
        return clientsRepo.findById(id).orElse(null);
    }

    @Override
    public boolean update(Clients client, long id) {
        if (read(id) != null) {
            Clients changeClient = new Clients();

            changeClient.setId(id);
            changeClient.setName(client.getName());
            changeClient.setSurname(client.getSurname());
            changeClient.setBirthday(client.getBirthday());
            changeClient.setEmail(client.getEmail());
            changeClient.setPhone(client.getPhone());

            clientsRepo.save(changeClient);
            System.out.println("updated");
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (read(id) != null) {
            clientsRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
