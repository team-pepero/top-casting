package com.ll.topcastingbe.domain.qna.post.dto;

import com.ll.topcastingbe.domain.qna.post.dto.response.FindPostResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record FindPostDto(Long id,
                          String title,
                          String content,
                          LocalDateTime createDate,
                          LocalDateTime modifyDate) {

    public static FindPostDto of(final FindPostResponse findPostResponse) {
        final FindPostDto findPostDto = FindPostDto.builder()
                .id(findPostResponse.id())
                .title(findPostResponse.title())
                .content(findPostResponse.content())
                .createDate(findPostResponse.createDate())
                .modifyDate(findPostResponse.modifyDate())
                .build();

        return findPostDto;
    }

    public static List<FindPostDto> ofList(final List<FindPostResponse> findPostResponseList) {
        final List<FindPostDto> findPostDtoList = findPostResponseList.stream()
                .map(FindPostDto::of)
                .toList();

        return findPostDtoList;
    }
}
