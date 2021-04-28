package com.student2students.postservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.student2students.postservice.constants.SequenceConstants;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = "posts")
public class Comment {
    @Id
    @SequenceGenerator(name = SequenceConstants.COMMENT_SEQUENCE, sequenceName = SequenceConstants.COMMENT_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.COMMENT_SEQUENCE)
    private Long id;

    @NotNull
    private String username;
    @NotNull
    @Column(name="body", columnDefinition="LONGTEXT")
    private String body;
    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
