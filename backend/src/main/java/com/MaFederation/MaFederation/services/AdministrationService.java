package com.MaFederation.MaFederation.services;

import com.MaFederation.MaFederation.model.Administration;
import com.MaFederation.MaFederation.model.Club;
import com.MaFederation.MaFederation.repository.AdminRepository;
import com.MaFederation.MaFederation.repository.ClubRepository;
import com.MaFederation.MaFederation.dto.Admin.PostAdminstrationDTO;
import com.MaFederation.MaFederation.dto.Admin.ResponceAdministrationDTO;
import com.MaFederation.MaFederation.mappers.AdministrationMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdministrationService {

    private final AdminRepository administrationRepository;
    private final ClubRepository clubRepository;
    private final AdministrationMapper administrationMapper;

    public Administration createAdministration(PostAdminstrationDTO dto) {
        Administration admin = administrationMapper.toEntity(dto);

        Club club = clubRepository.findById(dto.getClubId())
            .orElseThrow(() -> new RuntimeException("Club not found"));
        admin.setClub(club);

        return administrationRepository.save(admin);
    }

    public ResponceAdministrationDTO getById(Integer id) {
        Administration admin = administrationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Administration not found"));
            return administrationMapper.toDto(admin);
    }

    public ResponceAdministrationDTO update(Integer id, PostAdminstrationDTO dto) {
        Administration admin = administrationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Administration not found"));

        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());
        admin.setPhoneNumber(dto.getPhoneNumber());
        // admin.setFunction(dto.getFunction());

        Administration updated = administrationRepository.save(admin);
         return administrationMapper.toDto(updated);
    }

    public void delete(Integer id) {
        administrationRepository.deleteById(id);
    }



    //mod
    public List<ResponceAdministrationDTO> getAllClubsAdmins() {
        return administrationRepository.findAll().stream().map(a -> {
            ResponceAdministrationDTO dto = new ResponceAdministrationDTO();
            dto.setId(a.getId());
            dto.setFirstName(a.getFirstName());
            dto.setLastName(a.getLastName());
            dto.setEmail(a.getEmail());
            dto.setPost(a.getPost());
            dto.setClub(a.getClub().getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteAdmin(Integer id) {
        if (!administrationRepository.existsById(id)) {
            throw new IllegalArgumentException("Admin not found");
        }
        administrationRepository.deleteById(id);
    }












}
