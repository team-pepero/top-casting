package com.ll.topcastingbe.domain.notice.notice.service;


import com.ll.topcastingbe.domain.member.entity.Member;
import com.ll.topcastingbe.domain.notice.notice.dto.request.CreateNoticeRequest;
import com.ll.topcastingbe.domain.notice.notice.dto.request.ModifyNoticeRequest;
import com.ll.topcastingbe.domain.notice.notice.dto.response.FindNoticeResponse;
import com.ll.topcastingbe.domain.notice.notice.entity.Notice;
import com.ll.topcastingbe.domain.notice.notice.repository.NoticeRepository;
import com.ll.topcastingbe.domain.order.exception.BusinessException;
import com.ll.topcastingbe.domain.order.exception.ErrorMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public Long createNotice(final CreateNoticeRequest createNoticeRequest, final Member member) {
        final Notice notice = createNoticeRequest.toNotice(member);
        noticeRepository.save(notice);
        return notice.getId();
    }

    @Override
    public FindNoticeResponse findNotice(final Long noticeId) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND));
        final FindNoticeResponse findNoticeResponse = FindNoticeResponse.of(notice);
        return findNoticeResponse;
    }

    @Override
    @Transactional
    public void modifyNotice(final Long noticeId,
                             final ModifyNoticeRequest modifyNoticeRequest,
                             final Member member) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND));
        notice.checkAuthorizedMember(member);
        notice.modifyNotice(modifyNoticeRequest);
    }

    @Override
    @Transactional
    public void removeNotice(Long noticeId, Member member) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ENTITY_NOT_FOUND));
        notice.checkAuthorizedMember(member);
        noticeRepository.delete(notice);
    }

    @Override
    public List<FindNoticeResponse> findAllNotice() {
        final List<Notice> noticeList = noticeRepository.findAll();
        List<FindNoticeResponse> findNoticeResponse = FindNoticeResponse.ofList(noticeList);
        return findNoticeResponse;
    }
}
