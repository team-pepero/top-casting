package com.ll.topcastingbe.domain.notice.notice.dto;

import com.ll.topcastingbe.domain.notice.notice.dto.request.ModifyNoticeRequest;
import lombok.Builder;

@Builder
public record ModifyNoticeDto(String title, String content) {

    public ModifyNoticeRequest toModifyNoticeRequest(final ModifyNoticeDto modifyNoticeDto) {
        final ModifyNoticeRequest modifyNoticeRequest = ModifyNoticeRequest.builder()
                .title(title)
                .content(content)
                .build();

        return modifyNoticeRequest;
    }
}
