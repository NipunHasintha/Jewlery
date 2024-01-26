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
public class CustomerDTO {
    private String customer_id;
    private String firstName;
    private String lastName;
    private String income;
    private String martialstatus;
    private String customerstatus;
    private CommonStatus commonStatus;


}
