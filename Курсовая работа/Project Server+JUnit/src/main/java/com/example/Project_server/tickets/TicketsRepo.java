package com.example.Project_server.tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketsRepo extends JpaRepository<Tickets, Long> {

    @Query(value = "SELECT * FROM Tickets WHERE clients_id = :clients_id",
            nativeQuery = true)
    List<Tickets> findAllByClientId(@Param("clients_id") long client_id);

    @Query(value = "SELECT * FROM Tickets WHERE performances_id = :performances_id",
            nativeQuery = true)
    List<Tickets> findAllByPerformanceId(@Param("performances_id") long performance_id);

}
