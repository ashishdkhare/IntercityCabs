package com.bookmycab.entities;

import com.bookmycab.enums.CabState;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cabs")
@Setter
@Getter
@NoArgsConstructor
@Data
public class Cab {
    @Id
    @Column(name = "cabnumber", updatable = false, nullable = false)
    String cabNumber;

    @Column(name="cabstate")
    CabState cabstate;

    @Column(name="location")
    String location;

    Date lastupdated;
}
