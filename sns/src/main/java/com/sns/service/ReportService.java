package com.sns.service;

import com.sns.entity.ReportPost;
import com.sns.repository.ReportPostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportPostsRepository reportPostsRepository;

    public Page<ReportPost> getReportList(Pageable pageable) {
       return reportPostsRepository.findAll(pageable);
    }

}
