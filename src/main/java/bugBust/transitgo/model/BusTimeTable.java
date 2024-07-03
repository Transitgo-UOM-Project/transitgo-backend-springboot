//BusTimeTable.java
package bugBust.transitgo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class BusTimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message="Bus Id. is required")
    @NotBlank(message = "Bus Id. is required ")
    private int busId;
    @NotNull(message="Date is required")
    @NotBlank(message = "Date is required ")
    private LocalDate date;
    @NotNull(message="Status is required")
    @NotBlank(message = "Status is required ")
    private String status;
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
