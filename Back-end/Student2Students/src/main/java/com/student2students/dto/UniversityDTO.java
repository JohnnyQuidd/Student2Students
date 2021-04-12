package com.student2students.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityDTO {
    private Long id;
    private String universityName;
    private String universityEmail;
    // Address related fields
    private String country;
    private String city;
    private String streetName;
    private String streetNumber;
}
