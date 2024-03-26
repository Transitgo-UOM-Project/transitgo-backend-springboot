package bugBust.transitgo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class BusStops {
    @GeneratedValue
    @Id
    private Long id;

    private Long name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }
}
