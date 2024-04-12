package com.ll.topcastingbe.domain.qna.post.dto.response;

import com.ll.topcastingbe.domain.qna.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record FindPostResponse(Long id, String title, String content, LocalDateTime createDate,
                               LocalDateTime modifyDate) {

    public static FindPostResponse of(final Post post) {
        final FindPostResponse findPostResponse = FindPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createDate(post.getCreateDate())
                .modifyDate(post.getModifyDate())
                .build();

        return findPostResponse;
    }

    public static List<FindPostResponse> ofList(final List<Post> postList) {
        final List<FindPostResponse> findPostResponseList = postList.stream()
                .map(FindPostResponse::of)
                .toList();

        return findPostResponseList;
    }
}
