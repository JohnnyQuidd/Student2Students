package com.student2students.postservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.student2students.postservice.constants.DatabaseConstants;
import com.student2students.postservice.constants.SequenceConstants;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @SequenceGenerator(name = SequenceConstants.POST_SEQUENCE, sequenceName = SequenceConstants.POST_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.POST_SEQUENCE)
    private Long id;

    @NotNull
    private String studentUsername;

    @NotNull
    private String headline;

    @NotNull
    @Column(name="body", columnDefinition="LONGTEXT")
    private String body;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @NotNull
    @ManyToMany
    @JoinTable(name = DatabaseConstants.POST_TOPIC,
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<Mark> marks = new HashSet<>();
}
