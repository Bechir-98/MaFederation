package com.MaFederation.MaFederation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MaFederation.MaFederation.model.Staff;
import org.springframework.stereotype.Repository;


@Repository

public interface StaffRepository extends JpaRepository<Staff, Integer> 

{
   

}


