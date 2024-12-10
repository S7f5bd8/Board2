package com.example.board2.service;


import com.example.board2.dto.MemberDTO;
import com.example.board2.entity.Member;
import com.example.board2.entity.UserRole;
import com.example.board2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    @Override
    public void register(MemberDTO memberDTO) {
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(passwordEncoder.encode(memberDTO.getPassword())) // 암호화
                .role(UserRole.User_ROLE_USER) // 기본 ROLE_USER
                .build();

        memberRepository.save(member);
    }
}