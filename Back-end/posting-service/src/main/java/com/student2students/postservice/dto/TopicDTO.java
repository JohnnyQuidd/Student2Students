package com.student2students.postservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDTO {
    private String topicName;
    private String majorName;
}
