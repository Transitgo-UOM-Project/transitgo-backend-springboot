package bugBust.transitgo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BusRoute {

    @Id
    @Column(name="routeno")
    private String routeno;
    private String routename;







    public String getRouteno() {
        return routeno;
    }

    public void setRouteno(String routeno) {
        this.routeno = routeno;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }




}
