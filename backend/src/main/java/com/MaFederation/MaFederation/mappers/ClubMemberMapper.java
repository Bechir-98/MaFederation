//         package com.MaFederation.MaFederation.mappers;

//         import com.MaFederation.MaFederation.dto.ClubMember.PostClubMemberDTO;
//         import com.MaFederation.MaFederation.dto.ClubMember.ResponseClubMemberDTO;
//     import com.MaFederation.MaFederation.dto.User.ResponseUserDTO;
//     import com.MaFederation.MaFederation.model.Administration;
//         import com.MaFederation.MaFederation.model.Category;
//         import com.MaFederation.MaFederation.model.ClubMember;
//         import com.MaFederation.MaFederation.model.Player;
//         import com.MaFederation.MaFederation.model.Staff;
//         import com.MaFederation.MaFederation.repository.ClubRepository;
//         import com.MaFederation.MaFederation.services.CategoryService;
//         import org.springframework.beans.BeanUtils;
// import org.springframework.context.annotation.Lazy;

// import java.util.List;

//         import org.springframework.stereotype.Component;

//      @Component

// public class ClubMemberMapper {

//     private final CategoryService categoryService;
//     private final ClubRepository clubRepository;
//     private final UserMapper userMapper;

//     public ClubMemberMapper (CategoryService categoryService, @Lazy ClubRepository clubRepository,UserMapper userMapper )
//     {
//         this.categoryService=categoryService;
//         this.clubRepository=clubRepository;
//         this.userMapper=userMapper;

//     }

//     public ResponseClubMemberDTO toResponseDto(ClubMember member) {
//         if (member == null) return null;

//         ResponseUserDTO baseDto = userMapper.toResponseDto(member);

//         ResponseClubMemberDTO dto = new ResponseClubMemberDTO();

//         // Copy all common user fields
//         BeanUtils.copyProperties(baseDto, dto);

//         // Set ClubMember-specific fields
//         dto.setClubId(member.getClub() != null ? member.getClub().getId() : null);
//         dto.setType(member.getType());

//         dto.setCategories(
//             member.getCategories() != null
//                 ? member.getCategories().stream().map(Category::getName).toList()
//                 : List.of()
//         );

//         return dto;
//     }

//     public ClubMember toEntity(PostClubMemberDTO dto) {
//         if (dto == null) return null;

//         ClubMember member;
//         switch (dto.getType()) {
//             case "PLAYER" -> member = new Player();
//             case "STAFF" -> member = new Staff();
//             case "ADMIN" -> member = new Administration();
//             default -> throw new IllegalArgumentException("Unknown member type: " + dto.getType());
//         }

//         // Common User fields
//         member.setEmail(dto.getEmail());
//         member.setPasswordHash(dto.getPasswordHash());
//         member.setFirstName(dto.getFirstName());
//         member.setLastName(dto.getLastName());
//         member.setDateOfBirth(dto.getDateOfBirth());
//         member.setGender(dto.getGender());
//         member.setPhoneNumber(dto.getPhoneNumber());
//         member.setAddress(dto.getAddress());
//         member.setNationalID(dto.getNationalID());
//         member.setNationality(dto.getNationality());
//         member.setType(dto.getType());
//         member.setProfilePicture(dto.getProfilePicture());

//         member.setClub(clubRepository.findById(dto.getClubId()).orElse(null));
//         member.setCategories(categoryService.getCategoriesByIdsEntity(dto.getCategoryIds()));

//         return member;
//     }
// }
