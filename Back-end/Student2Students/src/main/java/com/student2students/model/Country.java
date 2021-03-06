package com.student2students.model;

import com.student2students.constants.SequenceConstants;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Country {
    @Id
    @SequenceGenerator(name = SequenceConstants.COUNTRY_SEQUENCE, sequenceName = SequenceConstants.COUNTRY_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.COUNTRY_SEQUENCE)
    private Long id;

    @NotNull
    private String country;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();
}
