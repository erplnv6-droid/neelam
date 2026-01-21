package com.SCM.NotifyRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.NotificationRequest.NotificationRequest;

public interface NotifyRepository extends JpaRepository<NotificationRequest,Long> {

}
