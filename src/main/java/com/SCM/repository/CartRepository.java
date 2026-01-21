package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.Cart;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	List<Cart> findByStaff(Staff staff);
	List<Cart> findByRetailer(Retailer retailer);
	List<Cart> findByDistributor(Distributor distributor);

}
