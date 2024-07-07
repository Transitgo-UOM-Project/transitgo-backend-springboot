//BusStop.java
package bugBust.transitgo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stopID;

    @NotNull(message="Bus Stop Name is required")
    @NotBlank(message = "Bus Stop Name is required ")
    private String name;

    @ManyToOne
    @JoinColumn(name = "routeno")
    private BusRoute busroute;


    private int orderIndex;



    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    @JsonBackReference
    public BusRoute getBusroute() {
        return busroute;
    }

    public void setBusroute(BusRoute busroute) {
        this.busroute = busroute;
    }

    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
