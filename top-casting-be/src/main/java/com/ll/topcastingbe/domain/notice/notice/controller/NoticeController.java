package com.ll.topcastingbe.domain.notice.notice.controller;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.notice.notice.dto.CreateNoticeDto;
import com.ll.topcastingbe.domain.notice.notice.dto.FindNoticeDto;
import com.ll.topcastingbe.domain.notice.notice.dto.ModifyNoticeDto;
import com.ll.topcastingbe.domain.notice.notice.dto.request.CreateNoticeRequest;
import com.ll.topcastingbe.domain.notice.notice.dto.request.ModifyNoticeRequest;
import com.ll.topcastingbe.domain.notice.notice.dto.response.FindNoticeResponse;
import com.ll.topcastingbe.domain.notice.notice.service.NoticeService;
import com.ll.topcastingbe.global.security.auth.PrincipalDetails;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class NoticeController {
    private final NoticeService noticeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/notice")
    public ResponseEntity<Void> noticeAdd(@Valid @RequestBody CreateNoticeDto createNoticeDto,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        final CreateNoticeRequest createNoticeRequest = createNoticeDto.toCreateNoticeRequest();
        final Long noticeId = noticeService.createNotice(createNoticeRequest, principalDetails.getMember());
        return ResponseEntity.created(URI.create(noticeId.toString())).build();
    }

    @GetMapping("/api/v1/notice/{noticeId}")
    public ResponseEntity<FindNoticeDto> noticeFind(@PathVariable("noticeId") Long noticeId) {
        final FindNoticeResponse findNoticeResponse = noticeService.findNotice(noticeId);
        final FindNoticeDto findNoticeDto = FindNoticeDto.of(findNoticeResponse);
        return ResponseEntity.ok(findNoticeDto);
    }

    @PatchMapping("/api/v1/notice/{noticeId}")
    public ResponseEntity<Void> noticeModify(@PathVariable("noticeId") Long noticeId,
                                             @Valid @RequestBody ModifyNoticeDto modifyNoticeDto,
                                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        final ModifyNoticeRequest modifyNoticeRequest =
                modifyNoticeDto.toModifyNoticeRequest(modifyNoticeDto);
        final Member member = principalDetails.getMember();
        noticeService.modifyNotice(noticeId, modifyNoticeRequest, member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/notice/{noticeId}")
    public ResponseEntity<Void> noticeDelete(@PathVariable("noticeId") Long noticeId,
                                             @AuthenticationPrincipal PrincipalDetails principalDetails) {

        final Member member = principalDetails.getMember();
        noticeService.removeNotice(noticeId, member);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/v1/notice")
    public ResponseEntity<List<FindNoticeDto>> noticeFindList() {

        final List<FindNoticeResponse> findNoticeResponseList = noticeService.findAllNotice();
        final List<FindNoticeDto> findNoticeDtoList = FindNoticeDto.ofList(findNoticeResponseList);
        return ResponseEntity.ok(findNoticeDtoList);
    }
}
