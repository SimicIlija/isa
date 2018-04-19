package com.isa.projekcije.model.dto;

import com.isa.projekcije.model.Role;
import com.isa.projekcije.model.User;

public class RoleDto {
    private String email;
    private Role role;
    private long id;

    public RoleDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static RoleDto createRoleDto(User user) {
        RoleDto roleDto = new RoleDto();
        roleDto.setEmail(user.getEmail());
        roleDto.setRole(user.getRole());
        roleDto.setId(user.getId());
        return roleDto;
    }
}
