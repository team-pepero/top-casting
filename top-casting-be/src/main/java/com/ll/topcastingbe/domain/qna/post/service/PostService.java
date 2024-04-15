package com.ll.topcastingbe.domain.qna.post.service;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.qna.post.dto.request.CreatePostRequest;
import com.ll.topcastingbe.domain.qna.post.dto.request.ModifyPostRequest;
import com.ll.topcastingbe.domain.qna.post.dto.response.FindPostResponse;
import java.util.List;

public interface PostService {
    Long createPost(final CreatePostRequest createPostRequest, final Member member);

    FindPostResponse findPost(final Long postId);

    void modifyPost(final Long postId, final ModifyPostRequest modifyPostRequest, final Member member);

    void removePost(final Long postId, final Member member);

    List<FindPostResponse> findAllPost();
}
