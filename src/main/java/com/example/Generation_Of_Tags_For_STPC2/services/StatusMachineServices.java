package com.example.Generation_Of_Tags_For_STPC2.services;

import com.example.Generation_Of_Tags_For_STPC2.models.StatusMachine;
import com.example.Generation_Of_Tags_For_STPC2.models.Tag;
import com.example.Generation_Of_Tags_For_STPC2.repositories.StatusMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusMachineServices {
    private final StatusMachineRepository statusMachineRepo;
    public StatusMachine getStatusMachineByID(Long IdMachine, String area) {
        StatusMachine machineStatus = statusMachineRepo.getStatusMachineByID(IdMachine, area);
        return machineStatus;
    }

    public StatusMachine updateStatusMachine(StatusMachine statusMachine) {
        return statusMachineRepo.saveAndFlush(statusMachine);
    }

    public List<StatusMachine> getDataForMasterTable(String area) {
        return statusMachineRepo.getDataForMasterTable(area);
    }

    public Integer getTabNum(String area, Integer MachineNumber) {
        return statusMachineRepo.getTabNum(area, MachineNumber);
    }

}
