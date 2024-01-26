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
public class Contact_infoDTO {
    private String Id;
    private String Email;
    private String Address1;
    private String Address2;
    private String City;
    private CommonStatus commonStatus;
}
