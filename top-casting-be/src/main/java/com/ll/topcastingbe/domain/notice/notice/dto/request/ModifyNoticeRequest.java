package com.ll.topcastingbe.domain.notice.notice.dto.request;

import lombok.Builder;

@Builder
public record ModifyNoticeRequest(String title, String content) {
}
