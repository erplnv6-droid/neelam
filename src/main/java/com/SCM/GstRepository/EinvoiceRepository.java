package com.SCM.GstRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.GstLoginUserDto.EinvoiceDto;
import com.SCM.GstModel.EinvoiceData;

public interface EinvoiceRepository extends JpaRepository<EinvoiceData, Long> {



}
