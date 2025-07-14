package com.jb.common_template.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "common_template")
public class CommonTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "column1")
    private Long id;

    //@Column(name = "column2")
    private String name;

    //@Column(name = "column3")
    private int age;

    //@Column(name = "column4")
    private boolean active;

    //@Column(name = "column5")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    //@Column(name = "column6")
    private Double salary;

    //@Column(name = "column7")
    private Float rating;

    //@Column(name = "column8")
    private BigDecimal amount;

    //@Column(name = "column9")
    private LocalDate localDate;

    //@Column(name = "column10")
    private LocalTime localTime;

    //@Column(name = "column11")
    private LocalDateTime localDateTime;

    //@Column(name = "column12")
    private Boolean verified;

    @Enumerated(EnumType.STRING)
    //@Column(name = "column13")
    private Status status;

    // Enum inside the class
    public enum Status 
    {
        ACTIVE, INACTIVE, PENDING
    }
}