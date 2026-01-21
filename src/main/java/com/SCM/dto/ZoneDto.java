package com.SCM.dto;

import com.SCM.model.Staff;
import com.SCM.model.State_Zone;

import lombok.Data;

import java.util.List;

@Data
public class ZoneDto {
	
    private int id;
    private String zoneName;
    private List<Staff> staff;
    private List<State_Zone>  state_zone;

 
}
