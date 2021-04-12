package com.student2students.postservice.model;

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
public class Mark {
    @Id
    @SequenceGenerator(name = SequenceConstants.MARK_SEQUENCE, sequenceName = SequenceConstants.MARK_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.MARK_SEQUENCE)
    private Long id;

    @NotNull
    private String studentUsername;

    @NotNull
    private int mark;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
