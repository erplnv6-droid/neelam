package com.SCM.dto;

import java.util.List;

import com.SCM.IndexDto.IndexVisitData;

import lombok.Data;

@Data
public class IndexStaffCheckinAndVisitData {

	private Long staffid;
	private String staffName;
	private String dayintime;
	private List<IndexVisitData> visit;
}
