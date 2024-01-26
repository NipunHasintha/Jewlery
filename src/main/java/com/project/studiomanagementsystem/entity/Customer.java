package com.project.studiomanagementsystem.entity;

import com.project.studiomanagementsystem.constant.CommonStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "Customer")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.project.studiomanagementsystem.util.SnowflakeIdGenerator")
    private Long customer_id;
    @Column

    private String firstName;
    private String lastName;
    private int income;
    private String martial_status;
    private String customer_status;
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "categoryId", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    @Enumerated
    private CommonStatus commonStatus;
}
