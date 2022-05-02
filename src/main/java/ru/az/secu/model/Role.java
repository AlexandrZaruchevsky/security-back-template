package ru.az.secu.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Set.of(Permission.USER_WRITE, Permission.USER_READ)),
    ADMIN(Set.of(Permission.ADMIN_WRITE, Permission.ADMIN_READ, Permission.USER_WRITE, Permission.USER_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
