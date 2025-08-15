package com.MaFederation.MaFederation.controllers;

import com.MaFederation.MaFederation.model.Role;
import com.MaFederation.MaFederation.services.RolePermissionService;
import com.MaFederation.MaFederation.dto.RolePermission.RoleDTO;
import com.MaFederation.MaFederation.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService service;

    // ----- Roles -----
    @GetMapping
    public List<Role> getRoles() {
        return service.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        return service.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Role createRole(@RequestBody RoleDTO dto) {
        return service.createRole(dto);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody RoleDTO dto) {
        return service.updateRole(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        service.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    // ----- Permissions -----
    @GetMapping("/permissions")
    public List<Permission> getPermissions() {
        return service.getAllPermissions();
    }

    @PostMapping("/permissions")
    public Permission createPermission(@RequestBody Permission permission) {
    
        return service.createPermission(permission);
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        service.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
