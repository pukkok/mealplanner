package com.training.mealplanner.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResult register(String username, String name, String password) {
        if (memberRepository.findByUsername(username) != null) {
            return RegisterResult.USERNAME_EXISTS;
        }

        try {
            String encodedPassword = passwordEncoder.encode(password);
            Member member = new Member(username, encodedPassword, name);
            memberRepository.save(member);
            return RegisterResult.SUCCESS;

        } catch (Exception e) {
            return RegisterResult.UNKNOWN_ERROR;
        }
    }

    public boolean login(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) return false;
        return passwordEncoder.matches(password, member.getPassword());
    }

    public String getNameByUsername(String username) {
        Member member = memberRepository.findByUsername(username);
        return member != null ? member.getName() : null;
    }
}
