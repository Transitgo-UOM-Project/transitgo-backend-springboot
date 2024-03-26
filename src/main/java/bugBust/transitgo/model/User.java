package bugBust.transitgo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String name;
    private String email;

    public USER_ROLE getRole() {
        return role;
    }

    public void setRole(USER_ROLE role) {
        this.role = role;
    }

//    public List<Announcement> getAnnouncements() {
//        return announcements;
//    }
//
//    public void setAnnouncements(List<Announcement> announcements) {
//        this.announcements = announcements;
//    }

    private USER_ROLE role;

//    @OneToMany
//    private List<Announcement> announcements = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
