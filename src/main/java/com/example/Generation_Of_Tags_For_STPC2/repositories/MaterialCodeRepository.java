package com.example.Generation_Of_Tags_For_STPC2.repositories;

import com.example.Generation_Of_Tags_For_STPC2.models.MaterialCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialCodeRepository extends JpaRepository<MaterialCode, Long> {
    @Query(value = "Select * from PRB.v_material_code vmc where vmc.code_ppb =:materialCode" , nativeQuery = true)
    List<MaterialCode> getDiameter(String materialCode);
}
