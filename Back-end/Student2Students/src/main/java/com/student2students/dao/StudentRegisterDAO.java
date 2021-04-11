package com.student2students.dao;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRegisterDAO {
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String streetName;
    private String streetNumber;
    private String email;
    private String username;
    private String password;
    private String language;
    private String biography;
}
