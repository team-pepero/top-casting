package com.ll.topcastingbe.domain.qna.post.dto.request;

import lombok.Builder;

@Builder
public record ModifyPostRequest(String title, String content) {
}
