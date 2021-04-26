package com.student2students.postservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.student2students.postservice.constants.SequenceConstants;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exchange {
    @Id
    @SequenceGenerator(name = SequenceConstants.EXCHANGE_SEQUENCE, sequenceName = SequenceConstants.EXCHANGE_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.EXCHANGE_SEQUENCE)
    private Long id;
    @NotNull
    private String studentUsername;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String university;
    @NotNull
    private int minNumberOfAttendees;
    @NotNull
    private int maxNumberOfAttendees;
    @NotNull
    private LocalDate exchangeStart;
    @NotNull
    private LocalDate exchangeEnding;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private float price;
}
