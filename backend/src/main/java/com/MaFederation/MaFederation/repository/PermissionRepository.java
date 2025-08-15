
package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
}