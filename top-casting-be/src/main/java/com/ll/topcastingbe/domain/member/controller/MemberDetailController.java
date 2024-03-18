package com.ll.topcastingbe.domain.member.controller;

import com.ll.topcastingbe.domain.member.dto.MemberDetailsResponseDto;
import com.ll.topcastingbe.domain.member.dto.MemberModifyRequestDto;
import com.ll.topcastingbe.domain.member.exception.PasswordAndPasswordCheckNotMatchException;
import com.ll.topcastingbe.domain.member.service.MemberService;
import com.ll.topcastingbe.global.security.auth.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberDetailController {

    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<?> memberDetails(@AuthenticationPrincipal PrincipalDetails principal) {
        final MemberDetailsResponseDto t = MemberDetailsResponseDto.toDto(principal.getMember());
//        return ResponseEntity.ok(MemberDetailsResponseDto.toDto(principal.getMember()));
        return ResponseEntity.ok(t);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("")
    public ResponseEntity<?> memberDetailsModify(@AuthenticationPrincipal PrincipalDetails principal,
//                                                 @PathVariablㅌe Long memberId,//PathVariable필요없음 있으면 500err남
                                                 @RequestBody @Valid MemberModifyRequestDto memberModifyDto) {

        //패스워드와 패스워드 확인이 일치하는지 체크
        if (!memberModifyDto.checkPasswordMatch()) {
            throw new PasswordAndPasswordCheckNotMatchException();
        }

        memberService.modifyMember(
                principal.getMember().getId(),
                memberModifyDto.getNickname(),
                memberModifyDto.getPassword(),
                memberModifyDto.getEmail(),
                memberModifyDto.getAddress1(),
                memberModifyDto.getAddress2(),
                memberModifyDto.getZipcode(),
                memberModifyDto.getPhoneNumber());

        return ResponseEntity.ok(null);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> memberRemove(@AuthenticationPrincipal PrincipalDetails principal,
                                          @PathVariable Long memberId) {
        memberService.removeMember(principal.getMember().getId());
        return ResponseEntity.ok(null);
    }
}
