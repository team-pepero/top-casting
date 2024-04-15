package com.ll.topcastingbe.domain.qna.post.controller;

import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.qna.post.dto.CreatePostDto;
import com.ll.topcastingbe.domain.qna.post.dto.FindPostDto;
import com.ll.topcastingbe.domain.qna.post.dto.request.CreatePostRequest;
import com.ll.topcastingbe.domain.qna.post.dto.response.FindPostResponse;
import com.ll.topcastingbe.domain.qna.post.service.PostService;
import com.ll.topcastingbe.global.security.auth.PrincipalDetails;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/notice")
    public ResponseEntity<Void> postAdd(@Valid @RequestBody CreatePostDto createPostDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        final CreatePostRequest createPostRequest = createPostDto.toCreatePostRequest();
        final Long postId = postService.createPost(createPostRequest, principalDetails.getMember());
        return ResponseEntity.created(URI.create("notice" + postId)).build();
    }

    @GetMapping("/api/v1/notice/{postId}")
    public ResponseEntity<FindPostDto> postFind(@PathVariable("postId") Long postId) {
        final FindPostResponse findPostResponse = postService.findPost(postId);
        final FindPostDto findPostDto = FindPostDto.of(findPostResponse);
        return ResponseEntity.ok(findPostDto);
    }
}
