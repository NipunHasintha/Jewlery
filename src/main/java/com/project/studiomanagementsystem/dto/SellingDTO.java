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
public class SellingDTO {
    private String Id;
    private String Weight;
    private String Price;
    private String GoldRate;
    private String carrots;
    private CommonStatus commonStatus;
    private CustomerDTO customer;
}
