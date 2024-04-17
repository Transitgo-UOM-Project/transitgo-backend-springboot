package bugBust.transitgo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class BusMgt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(unique = true)
    private String regNo;

    @ManyToOne
    @JoinColumn(name = "routeno")
    private BusRoute busroute;
    @JsonBackReference
    public BusRoute getBusroute() {
        return busroute;
    }

    public void setBusroute(BusRoute busroute) {
        this.busroute = busroute;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }


}
