package com.sns.repository;

import com.sns.dto.MainPostDto;
import com.sns.dto.PostFormDto;
import com.sns.dto.ProfilePostDto;
import com.sns.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sns.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{
/*
    @Query("SELECT p FROM Post p " +
            "JOIN MemberInterests mi ON p.member = mi.member " +
            "JOIN PostInterests pi ON p.postNo = pi.post.postNo " +
            "WHERE " +
            "   mi.member = :member " +
            "   AND ( " +
            "       (:develop = true OR pi.develop = true) " +
            "       or (:travel = true OR pi.travel = true) " +
            "       or (:animal = true OR pi.animal = true) " +
            "       or (:life = true OR pi.life = true) " +
            "       or (:food = true OR pi.food = true) " +
            "   )" +
            "ORDER BY p.regTime DESC")
    Page<MainPostDto> findPostsByInterests(
            @Param("member") Member member,
            @Param("develop") boolean develop,
            @Param("travel") boolean travel,
            @Param("animal") boolean animal,
            @Param("life") boolean life,
            @Param("food") boolean food,
            Pageable pageable
    );*/
    /*@Query (value = "SELECT DISTINCT m.nickname, p.subject, p.content, p.reg_time, p.post_no, p.created_by, pm.*\n" +
            "FROM posts p\n" +
            "         JOIN member m ON p.member_id = :member_id\n" +
            "         JOIN post_interests pi ON p.post_no = pi.post_no\n" +
            "         JOIN member_interests mi ON (pi.animal = :animal OR pi.develop = :develop OR pi.food = :food OR pi.life = :life OR pi.travel = :travel)\n" +
            "         LEFT JOIN post_image pm ON p.post_no = pm.post_no\n" +
            "ORDER BY p.reg_time DESC", nativeQuery = true)*/

    @Query(value = "SELECT distinct p.post_no AS postNo, m.nickname, p.subject, p.content, p.created_by AS createdBy, p.reg_time AS regTime, " +
            "pm.image_no AS imageNo, pm.image_name AS imageName, pm.image_url AS imageUrl, " +
            "pm.ori_image_name AS oriImageName, pm.rep_img_yn AS repImgYn " +
            "FROM posts p " +
            "JOIN member m ON p.member_id = :member_id " +
            "JOIN post_interests pi ON p.post_no = pi.post_no " +
            "JOIN member_interests mi ON (pi.animal = :animal OR pi.develop = :develop OR pi.food = :food OR pi.life = :life OR pi.travel = :travel) " +
            "LEFT JOIN post_image pm ON p.post_no = pm.post_no " +
            "ORDER BY p.reg_time DESC",
            nativeQuery = true)
    Page<MainPostDto> findPostsByInterests(
            @Param("member_id") Long memberId,
            @Param("develop") boolean develop,
            @Param("travel") boolean travel,
            @Param("animal") boolean animal,
            @Param("life") boolean life,
            @Param("food") boolean food,
            Pageable pageable
    );
    /*
    @Query(value = "SELECT distinct p.postNo AS postNo, m.nickname, p.subject, p.content, p.createdBy AS createdBy, p.regTime AS regTime " +
            "FROM Post p " +
            "JOIN Member m ON p.member.memberId = :member_id " +
            "JOIN PostInterests pi ON p.postNo = pi.post.postNo " +
            "JOIN MemberInterests mi ON (pi.animal = :animal OR pi.develop = :develop OR pi.food = :food OR pi.life = :life OR pi.travel = :travel) " +
            "ORDER BY p.regTime DESC"
            )
    Page<MainPostDto> findPostsByInterests(
            @Param("member_id") Long memberId,
            @Param("develop") boolean develop,
            @Param("travel") boolean travel,
            @Param("animal") boolean animal,
            @Param("life") boolean life,
            @Param("food") boolean food,
            Pageable pageable
    );*/

    List<Post> findByMember(Member member);


    Post findByPostNo(Long postNo);
}
