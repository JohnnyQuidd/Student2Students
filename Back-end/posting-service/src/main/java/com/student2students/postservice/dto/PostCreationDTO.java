package com.student2students.postservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreationDTO {
    private String headline;
    private String body;
    private String majorName;
    private List<String> topics;
}
