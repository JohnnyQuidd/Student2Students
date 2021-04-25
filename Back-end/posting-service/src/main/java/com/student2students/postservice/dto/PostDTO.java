package com.student2students.postservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long id;
    private String username;
    private LocalDateTime createdAt;
    private String headline;
    private String body;
    private String majorName;
    private List<String> topics;
}
