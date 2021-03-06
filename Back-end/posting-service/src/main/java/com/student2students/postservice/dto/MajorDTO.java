package com.student2students.postservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorDTO {
    private Long id;
    private String majorName;
}