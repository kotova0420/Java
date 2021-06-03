package sample.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tickets {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("client")
    @Expose
    private Clients client;

    @SerializedName("performance")
    @Expose
    private Performances performance;

    @SerializedName("share")
    @Expose
    private Shares share;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Shares getShare() {
        return share;
    }

    public void setShare(Shares share) {
        this.share = share;
    }
}
