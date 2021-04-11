package com.student2students.model;

import com.student2students.constants.SequenceConstants;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {
    @Id
    @SequenceGenerator(name = SequenceConstants.TOPIC_SEQUENCE, sequenceName = SequenceConstants.TOPIC_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.TOPIC_SEQUENCE)
    private Long id;

    @NotNull
    private String topicName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToMany(mappedBy = "topics")
    private Set<Post> posts = new HashSet<>();
}
