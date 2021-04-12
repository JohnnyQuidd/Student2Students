package com.student2students.postservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.student2students.postservice.constants.SequenceConstants;
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
@JsonIgnoreProperties(value = {"posts"})
public class Topic {
    @Id
    @SequenceGenerator(name = SequenceConstants.TOPIC_SEQUENCE, sequenceName = SequenceConstants.TOPIC_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.TOPIC_SEQUENCE)
    private Long id;

    @NotNull
    private String topicName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToMany(mappedBy = "topics")
    private Set<Post> posts = new HashSet<>();
}
