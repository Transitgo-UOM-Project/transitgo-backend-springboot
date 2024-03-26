package bugBust.transitgo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BusRoute {
@Id
@GeneratedValue
    private Long id;
    private String routeno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany
    private List<BusStops> busStops;

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

    private String routename;


}
