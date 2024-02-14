package com.example.Generation_Of_Tags_For_STPC2.repositories;

import com.example.Generation_Of_Tags_For_STPC2.models.StatusMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusMachineRepository extends JpaRepository<StatusMachine, Long> {
    @Query(value = "SELECT * FROM STATUS_MACHINE_GSV s\n" +
            "    WHERE s.ID_MACHINE =:IdMachine and s.UCHACTOK =:area", nativeQuery = true)
    StatusMachine getStatusMachineByID(Long IdMachine, String area);

    @Query(value = "SELECT * FROM STATUS_MACHINE_GSV s\n" +
            "    WHERE s.UCHACTOK =:area AND s.ID_MACHINE NOT IN (701,702,703,704,705,706,710)\n" +
            "ORDER BY s.ID_MACHINE", nativeQuery = true)
    List<StatusMachine> getDataForMasterTable(String area);

    @Query(value = "SELECT s.TABNOM_IN FROM STATUS_MACHINE_GSV s\n" +
            "    WHERE s.ID_MACHINE =:MachineNumber AND s.UCHACTOK =:area"
            , nativeQuery = true)
    Integer getTabNum(String area, Integer MachineNumber);

}

