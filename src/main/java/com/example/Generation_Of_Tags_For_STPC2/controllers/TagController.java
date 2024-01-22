package com.example.Generation_Of_Tags_For_STPC2.controllers;

import com.example.Generation_Of_Tags_For_STPC2.models.Tag;
import com.example.Generation_Of_Tags_For_STPC2.repositories.TagRepo;
import com.example.Generation_Of_Tags_For_STPC2.tagReports.TagReport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final TagRepo tagRepo;

    @GetMapping("/main")
    public String index() {
        /*File newFile = new File("C:/Users/egorov.BSW/Downloads/birka_GSV.xlsx");
        newFile.delete();*/
        return "main";
    }

    @GetMapping("/exportToExcel/{area}/{millNumber}/{materialCode}")
    public void exportIntoExcelFile(HttpServletResponse response, @PathVariable("area") String area,
                                    @PathVariable("millNumber") String millNumber,
                                    @PathVariable("materialCode") String materialCode) throws Exception {
        TagReport generator = new TagReport(tagRepo);
        generator.exportToExcel(area, millNumber, materialCode, response);
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