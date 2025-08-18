package com.MaFederation.MaFederation.repository;

import com.MaFederation.MaFederation.model.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByAgeMinLessThanEqualAndAgeMaxGreaterThanEqual(Integer minAge, Integer maxAge);

}
