package com.SCM.NotifyRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.NotificationRequest.NotificationDataCreate;

public interface NotifiySaveRepository extends JpaRepository<NotificationDataCreate,Long> {


}
