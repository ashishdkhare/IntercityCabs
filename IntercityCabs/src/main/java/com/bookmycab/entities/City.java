package com.bookmycab.entities;

import lombok.*;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cities", uniqueConstraints = { @UniqueConstraint(columnNames = { "city" }) })
public class City {

    @Id
    String city;

    @NonNull
    Double latitude;

    @NonNull
    Double longitude;
}
