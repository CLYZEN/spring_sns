package com.sns.repository;

import com.sns.dto.PostImgDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sns.entity.PostImage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long>{
    @Query(value = "SELECT pm FROM PostImage pm WHERE pm.post.postNo = :postNo")
    List<PostImgDto> findByPostPostNo(Long postNo);
}
