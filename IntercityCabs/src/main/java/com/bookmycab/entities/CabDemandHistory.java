package com.bookmycab.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// unused class at the moment but could be used to derive intelligence for surge timings/cities

@Entity
@Table(name = "cab_demand_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CabDemandHistory {
    @Id
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "reqcount", nullable = false)
    private Integer count;

    @Column(name = "houroftheday", nullable = false)
    private Integer houroftheday;

    @Column(name = "dayofthemonth", nullable = false)
    private Integer dayofthemonth;

    @Column(name = "monthoftheyear", nullable = false)
    private Integer monthoftheyear;
}
