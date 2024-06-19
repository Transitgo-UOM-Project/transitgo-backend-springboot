package bugBust.transitgo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class RateReviews {
    @Id
    @GeneratedValue
    private Long id;

    //valid to be notnull
    @Valid

    @NotNull(message="Rating is required")
    @NotBlank(message="Rating is required")
    private String rate;

    @NotNull(message="Review is required")
    @NotBlank(message="Review is required")
    @Size(min=4,message= "The review should be a minimum of 4 characters.")
    private String review;
    private String username;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    @PreUpdate
    public void setTimestamp() {
        this.createdAt = LocalDateTime.now();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
