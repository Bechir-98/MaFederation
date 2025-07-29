package com.MaFederation.MaFederation.dto.Category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubPostCategoryDTO {

    @NotNull(message = "Category ID must not be null")
    private Integer categoryId;
}
