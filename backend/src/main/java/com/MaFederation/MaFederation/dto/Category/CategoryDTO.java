package com.MaFederation.MaFederation.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO
    
 {
    Integer id;
    String name;
    String description;

    Integer ageMin;
    Integer ageMax;

}
