package com.bookmycab.helpers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CabHistoryId implements Serializable {
    Date datetime;
    String cabNumber;


}
