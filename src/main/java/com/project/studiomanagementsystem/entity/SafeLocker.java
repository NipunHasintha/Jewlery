package com.project.studiomanagementsystem.entity;

import com.project.studiomanagementsystem.constant.CommonStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "SafeLocker")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SafeLocker {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.project.studiomanagementsystem.util.SnowflakeIdGenerator")
    private Long Id;
    private String LockerName;
    private String Location;
    private Long Size;


    @Enumerated
    private CommonStatus commonStatus;

}
