package com.MaFederation.MaFederation.dto.RolePermission;

import java.util.Set;

import lombok.Data;
@Data
public class PostRoleDTO {
    private String name;
    private String description;
    private Set<Long> permissionIds; 
}
