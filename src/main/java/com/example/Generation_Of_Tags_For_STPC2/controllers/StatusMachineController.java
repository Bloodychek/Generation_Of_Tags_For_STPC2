package com.example.Generation_Of_Tags_For_STPC2.controllers;

import com.example.Generation_Of_Tags_For_STPC2.models.StatusMachine;
import com.example.Generation_Of_Tags_For_STPC2.services.StatusMachineServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class StatusMachineController {
    private final StatusMachineServices machineServices;
    @PatchMapping("main")
    public String handlerStatusMachine(
            @RequestParam("tabNum") Long tabNum,
            @RequestParam("machines[]") Long[] machines,
            @RequestParam("area") String area
    ) {
        StatusMachine machineStatus;

        for (int i = 0; i < machines.length; i++) {
            machineStatus = machineServices.getStatusMachineByID(machines[i],area);
                machineStatus.setTabNum(tabNum);
                machineServices.updateStatusMachine(machineStatus);
        }
        return "main";
    }
}
