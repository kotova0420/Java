package com.example.Project_server.tickets;

import com.example.Project_server.clients.Clients;
import com.example.Project_server.shares.Shares;
import com.example.Project_server.performances.Performances;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tickets {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "clients_id", referencedColumnName = "id")
    private Clients client;

    @ManyToOne
    @JoinColumn(name = "performances_id", referencedColumnName = "id")
    private Performances performance;

    @ManyToOne
    @JoinColumn(name = "shares_id", referencedColumnName = "id")
    @Nullable
    private Shares share;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public Performances getPerformance() {
        return performance;
    }

    public void setPerformance(Performances performance) {
        this.performance = performance;
    }

    @Nullable
    public Shares getShare() {
        return share;
    }

    public void setShare(@Nullable Shares share) {
        this.share = share;
    }
}
