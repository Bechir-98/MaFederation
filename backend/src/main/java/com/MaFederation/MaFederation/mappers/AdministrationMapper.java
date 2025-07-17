package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;
import com.MaFederation.MaFederation.dto.AdministrationDTO;
import com.MaFederation.MaFederation.model.Administration;

@Service
public class AdministrationMapper {

    public AdministrationDTO toDto(Administration admin) {
        if (admin == null) return null;

        return new AdministrationDTO(
            admin.getUserId(),
            admin.getRole()
        );
    }

    public Administration toEntity(AdministrationDTO dto) {
        if (dto == null) return null;

        Administration admin = new Administration();
        admin.setUserId(dto.userId());
        admin.setRole(dto.role());

        return admin;
    }
}
