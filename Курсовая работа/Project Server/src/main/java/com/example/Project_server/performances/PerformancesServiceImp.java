package com.example.Project_server.performances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformancesServiceImp implements PerformancesService{

    @Autowired
    private PerformancesRepo performancesRepo;

    @Override
    public Performances create(Performances performance) {
        return performancesRepo.save(performance);
    }

    @Override
    public List<Performances> readAll() {
        return performancesRepo.findAll();
    }

    @Override
    public Performances read(long id) {
        return performancesRepo.findById(id).orElse(null);
    }


    @Override
    public boolean update(Performances performance, long id) {
        if (read(id) != null) {
            Performances changePerformance = new Performances();

            changePerformance.setId(id);
            changePerformance.setName(performance.getName());
            changePerformance.setDate(performance.getDate());
            changePerformance.setTime(performance.getTime());
            changePerformance.setPrice(performance.getPrice());

            performancesRepo.save(changePerformance);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (read(id) != null) {
            performancesRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
