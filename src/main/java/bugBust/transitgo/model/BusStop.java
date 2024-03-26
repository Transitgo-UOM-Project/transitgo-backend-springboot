package bugBust.transitgo.model;

import jakarta.persistence.*;

@Entity
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stopID;

    private String name;



    public Long getStopID() {
        return stopID;
    }

    public void setStopID(Long stopID) {
        this.stopID = stopID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
