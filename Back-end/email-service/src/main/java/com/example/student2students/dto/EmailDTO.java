package com.example.student2students.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmailDTO {
    private String receiverEmail;
    private String content;
    private String subject;
}
