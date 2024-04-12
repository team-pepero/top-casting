package com.ll.topcastingbe.domain.qna.post.service;


import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import com.ll.topcastingbe.domain.qna.post.dto.request.CreatePostRequest;
import com.ll.topcastingbe.domain.qna.post.dto.request.ModifyPostRequest;
import com.ll.topcastingbe.domain.qna.post.dto.response.FindPostResponse;
import com.ll.topcastingbe.domain.qna.post.entity.Post;
import com.ll.topcastingbe.domain.qna.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Long createPost(final CreatePostRequest createPostRequest, final Member member) {
        final Post post = createPostRequest.toPost(member);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public FindPostResponse findPost(final Long postId, final Member member) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND));
        final FindPostResponse findPostResponse = FindPostResponse.of(post);
        return findPostResponse;
    }

    @Override
    public Long modifyPost(Long postId, ModifyPostRequest modifyPostRequest, Member member) {
        return null;
    }

    @Override
    public void removePost(Long postId, Member member) {

    }

    @Override
    public List<FindPostResponse> findAllPost() {
        return null;
    }
}
