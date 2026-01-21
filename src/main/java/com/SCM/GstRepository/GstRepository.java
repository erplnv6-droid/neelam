package com.SCM.GstRepository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstModel.EinvoiceData;
import com.SCM.GstModel.GstUserLogin;

public interface GstRepository extends JpaRepository<GstUserLogin, Long> {

//Boolean existsByGstin(String gstin);
	GstUserLogin findByGstin(String gstin);
	
@Transactional	
public void deleteByGstin(String gstin);
//	GstData save(String clientId, String string);

EinvoiceData save(EinvoiceData einvoiceDtoToEinvoice);



//	String save(String gstData);

//	GstData save(String gstData);

}
