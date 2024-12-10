package com.example.board2.repository;

import com.example.board2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email); // 이메일로 회원 검색
}
