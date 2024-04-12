package com.ll.topcastingbe.domain.qna.post.dto;

import com.ll.topcastingbe.domain.qna.post.dto.request.ModifyPostRequest;

public record ModifyPostDto(String title, String content) {

    public ModifyPostRequest toModifyPostDto(String title, String content) {
        final ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder()
                .title(title)
                .content(content)
                .build();

        return modifyPostRequest;
    }
}
