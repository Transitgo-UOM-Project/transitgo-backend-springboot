package bugBust.transitgo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    admin_read("admin:read"),
    admin_update("admin:update"),
    admin_create("admin:create"),
    admin_delete("admin:delete")
    ;

    @Getter
    private final String permission;
}
