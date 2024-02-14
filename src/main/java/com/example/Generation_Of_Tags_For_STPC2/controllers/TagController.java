package com.example.Generation_Of_Tags_For_STPC2.controllers;

import com.example.Generation_Of_Tags_For_STPC2.repositories.MaterialCodeRepo;
import com.example.Generation_Of_Tags_For_STPC2.repositories.TagRepo;
import com.example.Generation_Of_Tags_For_STPC2.services.StatusMachineServices;
import com.example.Generation_Of_Tags_For_STPC2.tagReports.TagReport;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final TagRepo tagRepo;
    private final StatusMachineServices machineServices;
    private final MaterialCodeRepo materialCodeRepo;

    @GetMapping("/main")
    public String index() {
        return "main";
    }

    @PostMapping("/exportToPdf")
    public String exportToPdf(@RequestParam("area") String area,
                              @Nullable @RequestParam("millNumber") String millNumber,
                              @Nullable @RequestParam("materialCode") String materialCode,
                              @Nullable @RequestParam("melt") Integer melt,
                              String diameter) throws Exception {
        TagReport gen = new TagReport(tagRepo, materialCodeRepo, machineServices);
        gen.exportToPdf(area, millNumber, materialCode, diameter, melt);
        return "redirect:/main";
    }
}