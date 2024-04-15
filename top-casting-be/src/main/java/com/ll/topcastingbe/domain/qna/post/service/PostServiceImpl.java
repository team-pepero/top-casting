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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Long createPost(final CreatePostRequest createPostRequest, final Member member) {
        final Post post = createPostRequest.toPost(member);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public FindPostResponse findPost(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND));
        final FindPostResponse findPostResponse = FindPostResponse.of(post);
        return findPostResponse;
    }

    @Override
    @Transactional
    public void modifyPost(final Long postId,
                           final ModifyPostRequest modifyPostRequest,
                           final Member member) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND));
        post.checkAuthorizedMember(member);
        post.modifyPost(modifyPostRequest);
    }

    @Override
    @Transactional
    public void removePost(Long postId, Member member) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND));
        post.checkAuthorizedMember(member);
        postRepository.delete(post);
    }

    @Override
    public List<FindPostResponse> findAllPost() {
        final List<Post> postList = postRepository.findAll();
        List<FindPostResponse> findPostResponse = FindPostResponse.ofList(postList);
        return findPostResponse;
    }
}
