package bugBust.transitgo.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

//@MappedSuperclass

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "[User]")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fname;

    private String lname;

    @Column(nullable = false)
    private String password;

    @Column(name = "username")
    private String uname;

    @Column(nullable = false, unique = true)
    private String email;

    private String busid;
    @Column(nullable = false)
    private boolean enabled;

    private String verificationToken;
    private String otp;
    private LocalDateTime otpTimestamp;
    private String phone;

    public Role getType() {
        return type;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role type;

    public void setType(Role type) {
        this.type = type;
    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return type.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
