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
public class Major {
    @Id
    @SequenceGenerator(name = SequenceConstants.FIELD_SEQUENCE, sequenceName = SequenceConstants.FIELD_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.FIELD_SEQUENCE)
    private Long id;

    @NotNull
    private String majorName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(mappedBy = "major")
    private Set<Post> posts = new HashSet<>();
}
