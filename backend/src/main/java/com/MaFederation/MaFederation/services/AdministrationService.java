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
    private  final LogsService logsService;
    private final AuthUtils authUtils ;

    public Administration createAdministration(PostAdminstrationDTO dto) {
        Administration admin = administrationMapper.toEntity(dto);

        Club club = clubRepository.findById(dto.getClubId())
            .orElseThrow(() -> new RuntimeException("Club not found"));
        admin.setClub(club);
        String user= authUtils.getCurrentUserId();
        logsService.log(club.getName() + "created asmin"+ admin.getId(), user);

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


        Administration updated = administrationRepository.save(admin);
        String user= authUtils.getCurrentUserId();
        logsService.log("admin "+ id +" was deleted ", user);
         return administrationMapper.toDto(updated);
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














}
