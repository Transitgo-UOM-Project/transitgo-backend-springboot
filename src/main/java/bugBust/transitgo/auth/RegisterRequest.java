package bugBust.transitgo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fname;
    private String lname;
    private String uname;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String password;
    private String email;
    private String type;

    private String busid;

    private String phone;

}
