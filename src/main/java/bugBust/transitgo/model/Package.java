package bugBust.transitgo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Random;

@Entity
public class Package {

    @Id
    private Long PackageID;
    private String BusID;
    private LocalDate ReceivedDate;
    private String Start;
    private String Destination;

    @NotNull(message="Receiver Name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Receiver name should contain only characters")
    private String ReceiverName;

    @NotNull(message = "NIC is required")
    @Pattern(regexp = "^[0-9]*V?$", message = "NIC should contain only digits followed by optional 'V'")
    private String ReceiverNIC;

    @NotNull(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{9,10}$", message = "Phone number should be 9 or 10 digits")
    private String ReceiverContact;

    private String employeeName;
    private String employeePhone;
    private Long employeeId;
    private String createdBy;
    private String Status;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getPackageID() {
        return PackageID;
    }

    public void setPackageID(Long packageID) {
        PackageID = packageID;
    }

    public String getBusID() {
        return BusID;
    }

    public void setBusID(String busID) {
        BusID = busID;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverNIC() {
        return ReceiverNIC;
    }

    public void setReceiverNIC(String receiverNIC) {
        ReceiverNIC = receiverNIC;
    }

    public String getReceiverContact() {
        return ReceiverContact;
    }

    public void setReceiverContact(String receiverContact) {
        ReceiverContact = receiverContact;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public LocalDate getReceivedDate() {
        return ReceivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        ReceivedDate = receivedDate;
    }

    @PrePersist
    protected void onCreate() {
        this.PackageID = generateRandomId();
    }

    private Long generateRandomId() {
        Random random = new Random();
        return 10000000L + random.nextInt(90000000); // Generate random Long between 10000000 and 99999999
    }
}
