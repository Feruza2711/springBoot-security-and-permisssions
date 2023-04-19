package uz.pdp.springbootsecurityproject.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionEnum implements GrantedAuthority {
    CATEGORY_ADD,
    CATEGORY_EDIT,
    CATEGORY_DELETE,
    CATEGORY_LIST,
    CATEGORY_ONE
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
