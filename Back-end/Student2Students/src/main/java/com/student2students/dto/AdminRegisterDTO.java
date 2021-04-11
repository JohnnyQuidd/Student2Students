package com.student2students.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRegisterDTO {
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String streetName;
    private String streetNumber;
    private String biography;
    private String email;
    private String username;
    private String password;
    private String language;
}
