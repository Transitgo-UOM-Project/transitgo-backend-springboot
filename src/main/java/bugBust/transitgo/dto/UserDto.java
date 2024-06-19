package bugBust.transitgo.dto;

import bugBust.transitgo.model.Role;
import bugBust.transitgo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String message;
    private int statusCode;
    private User user;
    private long id;

    private List<User> userList;

    private String fname;
    private String lname;
    private String uname;
    private String email;

    private Role type;

    private String otp;


}
