//BusRoute.java
package bugBust.transitgo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class BusRoute {

    @Id
    @Column(name="routeno")
    private int routeno;

    @NotNull(message="Route Name is required")
    @NotBlank(message = "Route Name is required ")
    private String routename;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "busroute", fetch = FetchType.LAZY)
    private List <BusMgt> buses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "busroute", fetch = FetchType.LAZY)
    private List <BusStop> busStops;

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public void setBusStops(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    public int getRouteno() {
        return routeno;
    }

    public void setRouteno(int routeno) {
        this.routeno = routeno;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    @JsonManagedReference
    public List<BusMgt> getBuses() {
        return buses;
    }

    public void setBuses(List<BusMgt> buses) {
        this.buses = buses;
    }
}
