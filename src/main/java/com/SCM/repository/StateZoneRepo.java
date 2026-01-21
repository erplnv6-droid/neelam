package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SCM.model.State_Zone;

@Repository
public interface StateZoneRepo extends JpaRepository<State_Zone, Integer> {}
