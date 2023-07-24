package com.sns.repository;

import com.sns.dto.PostImgDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sns.entity.PostImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long>{
    @Query(value = "SELECT pm FROM PostImage pm WHERE pm.post.postNo = :postNo")
    List<PostImgDto> findByPostPostNo(Long postNo);

    @Query("SELECT pm " +
           "FROM PostImage pm" +
           " where pm.post.postNo in (select p.postNo " +
           " from Post p, Member m " +
           " where p.member.memberId = :memberId)")
    List<PostImgDto> findByMemberId(@Param("memberId") Long memberId);
}
