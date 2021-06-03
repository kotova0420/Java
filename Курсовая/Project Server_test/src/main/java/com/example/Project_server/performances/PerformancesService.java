package com.example.Project_server.performances;

import java.util.List;

public interface PerformancesService {
    Performances create(Performances performance);

    List<Performances> readAll();

    Performances read(long id);

    boolean update(Performances performance, long id);

    boolean delete(long id);
}
