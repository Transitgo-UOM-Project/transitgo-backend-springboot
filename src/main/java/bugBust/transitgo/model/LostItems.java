package bugBust.transitgo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class LostItems {
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only alphabetic characters and spaces")
    private String name;

    @Pattern(regexp = "^(0?7(0|1|2|5|6|7|8)\\d{7})$", message = "Invalid mobile number")
    private String mobile_Number;
    private String createdBy;

    @Valid

    @NotNull(message="Bus description is required")
    @NotBlank(message = "Bus description is required ")
    @Size(min=8,message= "Bus description must be at least 8 characters.")
    private String bus_Description;

    @NotNull(message="Item description is required")
    @NotBlank(message = "Item description is required")
    @Size(min=3,message= "Item description must be at least 5 characters.")
    private String item_Description;
    private LocalDateTime dateTime;

    // Set the dateTime before persisting the entity
    @PrePersist
    protected void onCreate(){
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_Number() {
        return mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        this.mobile_Number = mobile_Number;
    }

    public String getBus_Description() {
        return bus_Description;
    }

    public void setBus_Description(String bus_Description) {
        this.bus_Description = bus_Description;
    }

    public String getItem_Description() {
        return item_Description;
    }

    public void setItem_Description(String item_Description) {
        this.item_Description = item_Description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
