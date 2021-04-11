package com.student2students.model;

import com.student2students.constants.SequenceConstants;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class University {
    @Id
    @SequenceGenerator(name = SequenceConstants.UNIVERSITY_SEQUENCE, sequenceName = SequenceConstants.UNIVERSITY_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.UNIVERSITY_SEQUENCE)
    private Long id;

    @NotNull
    private String universityName;

    @NotNull
    private String universityEmail;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address universityAddress;
}
