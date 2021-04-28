package com.student2students.postservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmailDTO {
    private String receiverEmail;
    private String receiverFirstName;
    private String content;
    private String subject;
    private String activationLink;
    private String studentCommenting;
    private String instruction;
}
