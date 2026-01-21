package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.model.PurchaseReturnItems;

public interface PurchaseReturnItemRepo extends JpaRepository<PurchaseReturnItems,Integer> {

}
