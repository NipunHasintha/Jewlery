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
public class InventoryDTO {
    private String Id;
    private String ItemName;
    private String GoldRate;
    private String Weight;
    private String Price;
    private String Carrots;
    private String Discount;
    private CommonStatus commonStatus;
}
