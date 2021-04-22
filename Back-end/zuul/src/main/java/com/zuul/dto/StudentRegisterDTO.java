package com.zuul.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String email;

    // Data for updating student's personal information
    private String biography;
    private String majorName;
    private String streetName;
    private String streetNumber;
    private String language;
}
