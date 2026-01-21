
package com.SCM.GstRepository;

import org.springframework.data.jpa.repository.JpaRepository;



import com.SCM.GstModel.EwayBillData;
import com.SCM.GstModel.GstUserLogin;

import com.SCM.GstModel.EwayBillData;
//import com.SCM.GstService.EwayBillAuth;


public interface EwayBillAuthRepository extends JpaRepository<EwayBillData, Long> {

	EwayBillData findByAuthToken(String auth_token);
	
	Boolean existsByAuthToken(String auth_token);
}
