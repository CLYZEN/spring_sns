package com.sns.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    @GetMapping(value = "/admin/reportList")
    public String getReportList() {

        return "/admin/reportList";
    }
}
