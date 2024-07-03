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

//    location
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private String delay;
    private String lastLeftStop;

    private String nextLocation;


    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getLastLeftStop() {
        return lastLeftStop;
    }

    public String getNextLocation() {
        return nextLocation;
    }


    public void setLastLeftStop(String lastLeftStop) {
        this.lastLeftStop = lastLeftStop;
    }

    public void setNextLocation(String nextLocation) {
        this.nextLocation = nextLocation;
    }


    @ManyToOne
    @JoinColumn(name = "routeno")
    private BusRoute busroute;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bus", fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bus", fetch = FetchType.LAZY)
    private List <RateReviews> reviews;


    @JsonManagedReference
    public List<RateReviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<RateReviews> reviews) {
        this.reviews = reviews;
    }

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


    public void updateStatusFromTimeTable(List<BusTimeTable> timeTables) {
        LocalDate today = LocalDate.now();
        LocalDate dateToCheck = today;
        String statusToUpdate = null;

        // Loop until a valid status is found or all dates are checked
        while (statusToUpdate == null) {
            LocalDate finalDateToCheck = dateToCheck;  // Make a final copy of dateToCheck for the lambda
            // Find the BusTimeTable entry for the current date to check
            BusTimeTable timeTable = timeTables.stream()
                    .filter(t -> t.getDate().isEqual(finalDateToCheck))
                    .findFirst()
                    .orElse(null);

            if (timeTable != null) {
                statusToUpdate = timeTable.getStatus();
            } else {
                // Go back one week
                dateToCheck = dateToCheck.minusWeeks(1);

                LocalDate limit = LocalDate.of(2024, 6, 10);
                if (dateToCheck.isBefore(limit)) {
                    break;
                }
            }
        }

        // Update the status with the found status or default to "off" if no valid status was found
        this.status = (statusToUpdate != null) ? statusToUpdate : "off";
    }





}
