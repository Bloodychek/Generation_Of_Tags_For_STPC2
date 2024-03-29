package com.example.Generation_Of_Tags_For_STPC2.services;

import com.example.Generation_Of_Tags_For_STPC2.models.StatusMachine;
import com.example.Generation_Of_Tags_For_STPC2.repositories.StatusMachineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusMachineServices {
    private final StatusMachineRepo statusMachineRepo;
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

    public Integer getTabNum(String area, String MachineNumber) {
        return statusMachineRepo.getTabNum(area, MachineNumber);
    }

}
