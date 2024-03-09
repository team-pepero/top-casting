package com.ll.topcastingbe.domain.member.service;

import com.ll.topcastingbe.domain.member.dto.JoinRequestDto;
import com.ll.topcastingbe.domain.member.entity.Address;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public void join(JoinRequestDto joinRequestDto) {
        Member member = Member.builder()
                                .username(joinRequestDto.getUsername())
                                .password(passwordEncoder.encode(joinRequestDto.getPassword()))
                                .nickname(joinRequestDto.getNickname())
                                .name(joinRequestDto.getName())
                                .email(joinRequestDto.getEmail())
                                .birthDate(joinRequestDto.getBirthDate())
                                .phoneNumber(joinRequestDto.getPhoneNumber())
                                .address(Address.builder()
                                                 .address(joinRequestDto.getAddress())
                                                 .zipcode(joinRequestDto.getZipcode())
                                                 .build())
                                .build();
        member.grantRole();

        memberRepository.save(member);
    }

    public boolean checkNickname(String nickname) {
        Optional<Member> om = memberRepository.findByNickname(nickname);
        if (om.isEmpty()) {
            return false;
        }
        return true;
    }

    public Member findUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
