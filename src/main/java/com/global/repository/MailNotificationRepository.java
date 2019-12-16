package com.global.repository;

import com.global.model.MailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailNotificationRepository extends JpaRepository<MailNotification, Long> {
}