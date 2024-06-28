package bugBust.transitgo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bugBust.transitgo.model.Permission.admin_read;
import static bugBust.transitgo.model.Permission.admin_update;
import static bugBust.transitgo.model.Permission.admin_create;
import static bugBust.transitgo.model.Permission.admin_delete;

@RequiredArgsConstructor
public enum Role {
    User(Collections.emptySet()),
    admin(
            Set.of(
                    admin_read,
                    admin_update,
                    admin_create,
                    admin_delete
            )
    ),
    passenger(
            Set.of(

            )
    ),
    employee(
            Set.of(

            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("Role" + this.name()));
        return authorities;
    }


}
