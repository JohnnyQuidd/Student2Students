package com.student2students.message_broker;

import com.student2students.dto.MajorDTO;
import com.student2students.dto.TopicDTO;
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
