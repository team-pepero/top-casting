package com.ll.topcastingbe.domain.member.controller;

import com.ll.topcastingbe.domain.member.dto.MemberModifyRequestDto;
import com.ll.topcastingbe.domain.member.exception.PasswordAndPasswordCheckNotMatchException;
import com.ll.topcastingbe.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberDetailController {

    private final MemberService memberService;

    //@PreAuthorize("isAuthenticated()")
    @PatchMapping("/{memberId}")
    public ResponseEntity<?> memberDetailsModify(//@AuthenticationPrincipal PrincipalDetails user,
                                                 @PathVariable Long memberId,
                                                 @RequestBody @Valid MemberModifyRequestDto memberModifyDto) {

        //패스워드와 패스워드 확인이 일치하는지 체크
        if (!memberModifyDto.checkPasswordMatch()) {
            throw new PasswordAndPasswordCheckNotMatchException();
        }

        memberService.modifyMember(
                //user.getMember().getId(),
                memberId,
                memberModifyDto.getNickname(),
                memberModifyDto.getPassword(),
                memberModifyDto.getEmail(),
                memberModifyDto.getAddress1(),
                memberModifyDto.getAddress2(),
                memberModifyDto.getZipcode(),
                memberModifyDto.getPhoneNumber());

        return ResponseEntity.ok(null);
    }
}
