package com.project.studiomanagementsystem.entity;

import com.project.studiomanagementsystem.constant.CommonStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ContactInfo")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.project.studiomanagementsystem.util.SnowflakeIdGenerator")
    private Long Id;
    private String Email;
    private String Address1;
    private String Address2;
    private String City;

    private AuditData auditData;

    @Enumerated
    private CommonStatus commonStatus;
}
