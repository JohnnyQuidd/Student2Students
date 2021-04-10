package com.student2students.model;

import com.student2students.constants.SequenceConstants;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Field {
    @Id
    @SequenceGenerator(name = SequenceConstants.FIELD_SEQUENCE, sequenceName = SequenceConstants.FIELD_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.FIELD_SEQUENCE)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "field")
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(mappedBy = "field")
    private Set<Post> posts = new HashSet<>();
}
