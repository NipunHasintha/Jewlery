package com.project.studiomanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.studiomanagementsystem.constant.CommonStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "Pawn")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pawn {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.project.studiomanagementsystem.util.SnowflakeIdGenerator")
    private Long Id;
    private Long netWeight;
    private Long grossWeight;
    private Long valueofItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    @Enumerated
    private CommonStatus commonStatus;
}
