package com.project.studiomanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.studiomanagementsystem.constant.CommonStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "Selling")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Selling {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.project.studiomanagementsystem.util.SnowflakeIdGenerator")
    private Long Id;
    private Long Weight;
    private Long Price;
    private Long GoldRate;
    private Long Carrots;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @Enumerated
    private CommonStatus commonStatus;


}
