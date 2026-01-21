package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.DeliveryChargeItems;

@Repository
public interface DeliverychargeritemsRepo extends JpaRepository<DeliveryChargeItems, Integer> {

    List<DeliveryChargeItems> findBySalesOrderItemsId(int salesOrderItemsId);

}
