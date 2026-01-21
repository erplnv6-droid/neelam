package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.SalesItems;

@Repository
public interface SalesItemsRepo extends JpaRepository<SalesItems, Integer> {

}
