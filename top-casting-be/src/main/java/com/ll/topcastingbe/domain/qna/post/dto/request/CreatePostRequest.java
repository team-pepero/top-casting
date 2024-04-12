package com.ll.topcastingbe.domain.qna.post.dto.request;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.qna.post.entity.Post;
import lombok.Builder;

@Builder
public record CreatePostRequest(String title, String content) {

    public Post toPost(final Member member) {
        final Post post = Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

        return post;
    }
}
