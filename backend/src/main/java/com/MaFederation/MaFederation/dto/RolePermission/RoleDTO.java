package com.MaFederation.MaFederation.dto.RolePermission;
import com.MaFederation.MaFederation.enums.RoleName;
import lombok.Data;
import java.util.Set;

@Data
public class RoleDTO {
    private Long id;
    private RoleName name;
    private String description;
    private Set<Long> permissionIds; 
}

