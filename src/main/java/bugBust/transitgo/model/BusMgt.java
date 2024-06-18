//BusMgt.java
package bugBust.transitgo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Entity
public class BusMgt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(unique = true)
    private String regNo;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "routeno")
    private BusRoute busroute;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bus", fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    @JsonManagedReference
    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @JsonBackReference
    public BusRoute getBusroute() {
        return busroute;
    }


    public int getRouteNo(){
        return busroute.getRouteno();
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
// Inside BusMgt entity

    public void updateStatusFromTimeTable(List<BusTimeTable> timeTables) {
        // Assuming you want the latest status, you can iterate through timeTables to find the latest status
        BusTimeTable latestTimeTable = timeTables.stream()
                .max(Comparator.comparing(BusTimeTable::getDate)) // Assuming date is the timestamp to determine latest
                .orElse(null);

        if (latestTimeTable != null) {
            this.status = latestTimeTable.getStatus();
        }
    }

    public String getStatusOnDate(LocalDate date) {

        return "";
    }


}
