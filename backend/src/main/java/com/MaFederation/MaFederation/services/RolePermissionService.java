package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.model.Role;
import com.MaFederation.MaFederation.dto.RolePermission.RoleDTO;
import com.MaFederation.MaFederation.model.Permission;
import com.MaFederation.MaFederation.repository.RoleRepository;
import com.MaFederation.MaFederation.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Transactional
    public Role createRole(RoleDTO dto) {
        Set<Permission> permissions = new HashSet<>();
        if(dto.getPermissionIds() != null) {
            permissions = new HashSet<>(permissionRepository.findAllById(dto.getPermissionIds()));
        }

        Role role = Role.builder()
                .name(dto.getName()) // already a RoleName
                .description(dto.getDescription())
                .permissions(permissions)
                .build();


        return roleRepository.save(role);
    }

    @Transactional
    public Role updateRole(Long roleId, RoleDTO dto) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(dto.getName());
        role.setDescription(dto.getDescription());

        Set<Permission> permissions = new HashSet<>();
        if(dto.getPermissionIds() != null) {
            permissions = new HashSet<>(permissionRepository.findAllById(dto.getPermissionIds()));
        }
        role.setPermissions(permissions);

        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Transactional
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
