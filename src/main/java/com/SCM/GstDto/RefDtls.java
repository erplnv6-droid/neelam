package com.SCM.GstDto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RefDtls {
	
	private String InvRm;
	
	private DocPerdDtls DocPerdDtls;
	
	private List<PrecDocDtls> PrecDocDtls;
	
	private List<ContrDtls> ContrDtls;

	




}
