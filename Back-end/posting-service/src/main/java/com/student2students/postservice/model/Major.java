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
@JsonIgnoreProperties(value = {"posts", "topics"})
public class Major {
    @Id
    @SequenceGenerator(name = SequenceConstants.MAJOR_SEQUENCE, sequenceName = SequenceConstants.MAJOR_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.MAJOR_SEQUENCE)
    private Long id;

    @NotNull
    private String majorName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(mappedBy = "major")
    private Set<Post> posts = new HashSet<>();
}
