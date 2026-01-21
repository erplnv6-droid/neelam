package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.model.CartonBarcodeItems;

public interface CartonBarcodeItemRepo extends JpaRepository<CartonBarcodeItems, Integer>{

	
}
