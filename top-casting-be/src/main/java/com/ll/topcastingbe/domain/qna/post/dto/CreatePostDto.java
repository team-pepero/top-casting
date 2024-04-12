package com.ll.topcastingbe.domain.qna.post.dto;

import com.ll.topcastingbe.domain.qna.post.dto.request.CreatePostRequest;
import lombok.Builder;

@Builder
public record CreatePostDto(String title, String content) {
    public CreatePostRequest toCreatePostRequest() {
        final CreatePostRequest createPostRequest = CreatePostRequest.builder()
                .title(title)
                .content(content)
                .build();

        return createPostRequest;
    }
}
