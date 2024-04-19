package com.ll.topcastingbe.domain.notice.notice.dto;

import com.ll.topcastingbe.domain.notice.notice.dto.request.CreateNoticeRequest;
import lombok.Builder;

@Builder
public record CreateNoticeDto(String title, String content) {
    public CreateNoticeRequest toCreateNoticeRequest() {
        final CreateNoticeRequest createNoticeRequest = CreateNoticeRequest.builder()
                .title(title)
                .content(content)
                .build();

        return createNoticeRequest;
    }
}
