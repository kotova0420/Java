package com.example.Project_server.tickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsServiceImp implements TicketsService{

    @Autowired
    private TicketsRepo ticketsRepo;

    @Override
    public Tickets create(Tickets ticket) {
        return ticketsRepo.save(ticket);
    }

    //почему data time также у клиентов
    @Override
    public List<Tickets> readAll() {
        return ticketsRepo.findAll(Sort.by(Sort.Direction.ASC, "date", "time"));
    }

    @Override
    public Tickets read(long id) {
        return ticketsRepo.findById(id).orElse(null);
    }

    @Override
    public boolean update(Tickets ticket, long id) {
        if (read(id) != null) {

            Tickets changeTickets = new Tickets();

            changeTickets.setId(id);
            changeTickets.setClient(ticket.getClient());
            changeTickets.setPerformance(ticket.getPerformance());
            changeTickets.setShare(ticket.getShare());

            ticketsRepo.save(changeTickets);
            return true;
        }
        return false;
    }


    @Override
    public boolean delete(long id) {
        if (read(id) != null) {
            ticketsRepo.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<Tickets> findAllByClientId(long id) {
        return ticketsRepo.findAllByClientId(id);
    }

    @Override
    public boolean deleteByClientId(long id) {
        ticketsRepo.deleteAll(findAllByClientId(id));
        return true;
    }


    @Override
    public List<Tickets> findAllByPerformanceId(long id) {
        return ticketsRepo.findAllByPerformanceId(id);
    }

    @Override
    public boolean deleteByPerformanceId(long id) {
        ticketsRepo.deleteAll(findAllByPerformanceId(id));
        return true;
    }

}
