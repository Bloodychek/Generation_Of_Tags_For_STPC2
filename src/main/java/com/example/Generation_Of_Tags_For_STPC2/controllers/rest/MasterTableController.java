package com.example.Generation_Of_Tags_For_STPC2.controllers.rest;

import com.example.Generation_Of_Tags_For_STPC2.models.StatusMachine;
import com.example.Generation_Of_Tags_For_STPC2.services.StatusMachineServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MasterTableController {
    private final StatusMachineServices machineServices;
    @GetMapping("/masterTable/H")
    public List<StatusMachine> getDataForMasterTableH() {
        return machineServices.getDataForMasterTable("H");
    }

    @GetMapping("/masterTable/D")
    public List<StatusMachine> getDataForMasterTableD() {
        return machineServices.getDataForMasterTable("D");
    }

}
