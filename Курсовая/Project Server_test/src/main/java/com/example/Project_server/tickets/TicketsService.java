package com.example.Project_server.tickets;

import java.util.List;

public interface TicketsService {

    Tickets create(Tickets ticket);

    List<Tickets> readAll();

    Tickets read(long id);

    boolean update(Tickets ticket, long id);

    boolean delete(long id);



    List<Tickets> findAllByClientId(long id);

    boolean deleteByClientId(long id);

    List<Tickets> findAllByPerformanceId(long id);

    boolean deleteByPerformanceId(long id);

}
