package com.student2students.postservice.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeDTO {
    private String country;
    private String city;
    private String university;
    private int minNumberOfAttendees;
    private int maxNumberOfAttendees;
    private LocalDate exchangeStart;
    private LocalDate exchangeEnding;
    private float price;
    private String body;
}
