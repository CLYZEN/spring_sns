package com.sns.repository;

import com.sns.entity.ReportPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportPostsRepository extends JpaRepository<ReportPost, Long> {

    Page<ReportPost> findAll(Pageable pageable);
}
