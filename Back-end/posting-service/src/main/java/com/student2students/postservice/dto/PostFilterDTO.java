package com.student2students.postservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostFilterDTO {
    private String majorName;
    private List<String> topics;
}
