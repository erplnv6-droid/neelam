package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.CartonBarcodeActivityLog;

@Repository
public interface CartonBarcodeActivityLogRepo extends JpaRepository<CartonBarcodeActivityLog, Integer> {

}
