package com.SCM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffLocationDto {
    private Long staffId;
    private Double latitude;
    private Double longitude;
    private String lastUpdated; // LocalDateTime -> String
}