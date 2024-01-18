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
    public void exportIntoExcelFile(HttpServletResponse response, @PathVariable(value = "area") String area,
                                      @PathVariable(value = "millNumber") String millNumber,
                                      @PathVariable(value = "materialCode") String materialCode, Model model) throws Exception {
        List<Tag> tagList = tagRepo.findByAreaAndMillNumberAndMaterialCode(area, millNumber, materialCode);
        TagReport generator = new TagReport(tagRepo);
        generator.exportToExcel(area, millNumber, materialCode, response);

        model.addAttribute("tagList", tagList);
    }
}