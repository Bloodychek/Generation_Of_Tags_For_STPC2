package com.example.Generation_Of_Tags_For_STPC2.controllers;

import com.example.Generation_Of_Tags_For_STPC2.repositories.MaterialCodeRepository;
import com.example.Generation_Of_Tags_For_STPC2.repositories.TagRepo;
import com.example.Generation_Of_Tags_For_STPC2.services.StatusMachineServices;
import com.example.Generation_Of_Tags_For_STPC2.tagReports.TagReport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final TagRepo tagRepo;
    private final StatusMachineServices machineServices;
    private final MaterialCodeRepository materialCodeRepository;

    @GetMapping("/main")
    public String index() {
        return "main";
    }

    @PostMapping("exportToExcel")
    public void exportIntoExcelFile(HttpServletResponse response,
                                    @RequestParam("area") String area,
                                    @RequestParam("millNumber") String millNumber,
                                    @RequestParam("materialCode") String materialCode,
                                    @RequestParam("melt") Integer melt
//                                    @Nullable @RequestParam("diameter") Integer diameter,
//                                    @Nullable @RequestParam("operator") Integer operator
//                                    @Validated Tag tag,
    ) throws Exception {
        String diameter = materialCodeRepository.getDiameter(materialCode).get(0).getDiameter();
        Integer millNum = Integer.valueOf(millNumber);
        Integer operator = machineServices.getTabNum(area,millNum);
        TagReport gen = new TagReport(tagRepo);
        gen.exportToExcel(area, millNum, materialCode, diameter, operator, response);
    }
}