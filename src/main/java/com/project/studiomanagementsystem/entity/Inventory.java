package com.project.studiomanagementsystem.entity;

import com.project.studiomanagementsystem.constant.CommonStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Inventory")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.project.studiomanagementsystem.util.SnowflakeIdGenerator")
    private Long Id;
    private String ItemName;
    private Long GoldRate;
    private Long Weight;
    private Long Price;
    private Long Carrots;
    private Long Discount;
    @Enumerated
    private CommonStatus commonStatus;
}
