package com.sns.controller;

import com.sns.entity.ReportPost;
import com.sns.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = "/admin/reportList")
    public String getReportList(Model model, Optional<Integer> page) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 10);
        Page<ReportPost> reportPosts = reportService.getReportList(pageable);

        model.addAttribute("reportPosts", reportPosts);
        model.addAttribute("maxPage", 5);

        return "admin/reportList";
    }
}
