package com.bookmycab.entities;

import com.bookmycab.enums.CabState;
import com.bookmycab.helpers.CabHistoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cab_history")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CabHistoryId.class)
public class CabHistory implements Serializable {
    @Id
    Date datetime;
    @Id
    @Column(name = "cabnumber", updatable = false, nullable = false)
    String cabNumber;

    CabState cabstate;

    String location;
}

// T1 -> cab is booked -> trip ended -> cab booked again -> trip ended -> T2
