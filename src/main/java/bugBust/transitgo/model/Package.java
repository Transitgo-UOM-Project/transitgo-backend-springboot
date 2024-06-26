package bugBust.transitgo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import java.time.LocalDate;



@Entity
public class Package {

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Id
    @GeneratedValue
    private Long PackageID;
    private String BusID;
    private LocalDate ReceivedDate;
    private String Start;
    private String Destination;
    @NotNull(message="Receiver Name is required")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "Receiver name should contain only characters")
    private String ReceiverName;

    @NotNull(message = "NIC is required")
    @Pattern(regexp = "^[0-9]*V?$")
    private String ReceiverNIC;
    private String employeeName;
    private String employeePhone;
    private Long employeeId;

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

    private String createdBy;

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

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
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

    @NotNull(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number should be 10 digits")
    private String ReceiverContact;
    private String Payment;
    private String Status;
}
