package com.student2students.postservice.message_broker;

import com.student2students.postservice.dto.MajorDTO;
import com.student2students.postservice.dto.TopicDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageInstruction {
    private String instructionName;
    private TopicDTO topicDTO;
    private MajorDTO majorDTO;
}
