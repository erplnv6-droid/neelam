package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.Visit;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Integer> {

}
