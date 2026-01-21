package com.SCM.dto;

import lombok.Getter;
import lombok.Setter;

public interface LocationDto1 {

	  long getid();
	  String getcheckinLocationDate();
	  String getcheckinLocation();
	  String getcheckinLocationLatitude();
	  String getcheckinLocationLongitude();
	  String getcheckoutLocationDate();
	  String getcheckoutLocation();
	  String getcheckoutLocationLatitude();
	  String getcheckoutLocationLongitude();
	
	  String  getstaffname();
	  String getretailer();
	  String getdistributor();

}