package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.model.SalesExpenseImages;
import com.SCM.model.SalesExpenseItems;

@Repository
public interface SalesExpenseImageRepo extends JpaRepository<SalesExpenseImages, Integer> {

	SalesExpenseImages findBySalesexpfilenameAndSalesexpfilelocation(String originalFilename, String string);

	List<SalesExpenseImages> findBySalesExpenseItems(SalesExpenseItems salesExpenseItems);

	@Query(value = "SELECT DISTINCT img.id,img.salesexpfilename,img.salesexpfilelocation,img.salesexpitems_id\r\n"
			+ "FROM \r\n"
			+ "sales_expense_images img\r\n"
			+ "LEFT JOIN sales_expense_items sei ON img.salesexpitems_id = sei.id\r\n"
			+ "LEFT JOIN sales_expense se ON sei.salesexp_id = se.id\r\n"
			+ "WHERE se.id = ?1",nativeQuery = true)
    List<SalesExpenseImages> getsalesexpenseimagebysalesitemid(int itemid);
}
