package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.CartItems;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {

}
