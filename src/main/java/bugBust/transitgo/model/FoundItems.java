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


@Entity  //will create the table once we connect db
public class FoundItems {

    @Id  // to generate
    @GeneratedValue // id auto generate
    private Long id;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only alphabetic characters and spaces")
    private String Name;

    @Pattern(regexp = "^(0?7(0|1|2|5|6|7|8)\\d{7})$", message = "Invalid mobile number")
    private String Mobile_Number;

    @Valid

    @NotNull(message="Bus description is required")
    @NotBlank(message = "Bus description is required ")
    @Size(min=8,message= "Bus description must be at least 8 characters.")
    private String Bus_Description;

    @NotNull(message="Item description is required")
    @NotBlank(message = "Item description is required")
    @Size(min=3,message= "Item description must be at least 5 characters.")
    private String Item_Description;

    private LocalDateTime dateTime;
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String getBus_Description() {
        return Bus_Description;
    }

    public void setBus_Description(String bus_Description) {
        Bus_Description = bus_Description;
    }

    public String getItem_Description() {
        return Item_Description;
    }

    public void setItem_Description(String item_Description) {
        Item_Description = item_Description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


}
