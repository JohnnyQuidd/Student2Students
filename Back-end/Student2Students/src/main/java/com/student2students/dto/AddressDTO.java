package com.student2students.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String country;
    private String city;
    private String streetName;
    private String streetNumber;
}
