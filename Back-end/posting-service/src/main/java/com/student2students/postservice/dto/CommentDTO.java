package com.student2students.postservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private Long postId;
    private String username;
    private String body;
    private LocalDateTime createdAt;
}
