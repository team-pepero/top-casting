package com.ll.topcastingbe.domain.notice.notice.repository;

import com.ll.topcastingbe.domain.notice.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
