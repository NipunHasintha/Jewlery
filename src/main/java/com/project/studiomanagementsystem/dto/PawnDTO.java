package com.project.studiomanagementsystem.dto;

import com.project.studiomanagementsystem.constant.CommonStatus;
import com.project.studiomanagementsystem.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PawnDTO {
    private String Id;
    private String  netWeight;
    private String grossWeight;
    private String valueofItem;
    private CustomerDTO customer;
    private CommonStatus commonStatus;
}
