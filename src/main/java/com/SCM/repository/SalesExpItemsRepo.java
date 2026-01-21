package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.model.SalesExpenseItems;
import java.util.List;


public interface SalesExpItemsRepo extends JpaRepository<SalesExpenseItems, Integer> {
	
	
	@Query(value="select * from sales_expense_items where salesexp_id=?",nativeQuery = true)
	List<SalesExpenseItems> findBySalesexpId(int salesexpid);

	
}
