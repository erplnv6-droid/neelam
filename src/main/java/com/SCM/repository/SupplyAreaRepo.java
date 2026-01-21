package com.SCM.repository;

import com.SCM.model.SupplyArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyAreaRepo extends JpaRepository<SupplyArea,Integer> {
}
