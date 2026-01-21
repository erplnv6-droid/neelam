package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.GstModel.SetBarcodeActivityLog;
import com.SCM.model.SetBarcode;

@Repository
public interface SetBarcodeActivityLogRepo extends JpaRepository<SetBarcodeActivityLog, Integer> {

}
