package com.example.Generation_Of_Tags_For_STPC2.controllers;

import com.example.Generation_Of_Tags_For_STPC2.repositories.TagRepo;
import com.example.Generation_Of_Tags_For_STPC2.tagReports.TagReport;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final TagRepo tagRepo;

    @GetMapping("/main")
    public String index() {
        return "main";
    }

    @PostMapping("/exportToPdf")
    public String exportIntoExcelFile(@RequestParam("area") String area,
                                      @RequestParam("millNumber") String millNumber,
                                      @RequestParam("materialCode") String materialCode,
                                      @Nullable @RequestParam("diameter") Integer diameter,
                                      @Nullable @RequestParam("operator") Integer operator,
                                      @Nullable @RequestParam("melt") Integer melt,
                                      Model model) throws Exception {
        model.addAttribute("area", area);
        model.addAttribute("millNumber", millNumber);
        model.addAttribute("materialCode", materialCode);
        model.addAttribute("diameter", diameter);
        model.addAttribute("operator", operator);
        model.addAttribute("melt", melt);

        TagReport gen = new TagReport(tagRepo);
        gen.exportToPdf(area, millNumber, materialCode, diameter, operator, melt);
        return "redirect:/main";
    }

    @GetMapping("/exp-by-ar-mill-mat")
    public String tagParam(@RequestParam("area") String area,
                           @RequestParam("millNumber") String millNumber,
                           @RequestParam("materialCode") String materialCode,
                           Model model) {
        model.addAttribute("area", area);
        model.addAttribute("millNumber", millNumber);
        model.addAttribute("materialCode", materialCode);
        return "main";
    }
}