package com.project.studiomanagementsystem.dto;

import com.project.studiomanagementsystem.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Safe_LockerDTO {
    private String Id;
    private String LockerName;
    private String Location;
    private String Size;
    private CommonStatus commonStatus;
}
