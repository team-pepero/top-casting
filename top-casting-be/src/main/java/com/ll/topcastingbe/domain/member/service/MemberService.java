package com.ll.topcastingbe.domain.member.service;

import com.ll.topcastingbe.domain.member.dto.JoinRequestDto;
import com.ll.topcastingbe.domain.member.entity.Address;
import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.member.exception.PasswordNotMatchException;
import com.ll.topcastingbe.domain.member.exception.UserNotFoundException;
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
                        .address1(joinRequestDto.getAddress1())
                        .address2(joinRequestDto.getAddress2())
                        .zipcode(joinRequestDto.getZipcode())
                        .build())
                .build();

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

    @Transactional
    public void modifyMember(Long memberId, String nickname, String password, String email, String address1,
                             String address2, String zipcode, String phoneNumber) {
        //패스워드가 실제 member 패스워드와 일치하는지 확인
        Member findMember = validateAndFindMember(memberId, password);

        Address address = Address.builder()
                .address1(address1)
                .address2(address2)
                .zipcode(zipcode).build();

        findMember.changeDetails(nickname, email, address, phoneNumber);
    }

    private Member validateAndFindMember(Long memberId, String password) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException());

        if (!passwordEncoder.matches(password,member.getPassword())) {
            throw new PasswordNotMatchException();
        }
        return member;
    }
}
