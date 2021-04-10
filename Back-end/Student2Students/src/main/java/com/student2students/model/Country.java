package com.student2students.model;

import com.student2students.constants.SequenceConstants;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Country {
    @Id
    @SequenceGenerator(name = SequenceConstants.COUNTRY_SEQUENCE, sequenceName = SequenceConstants.COUNTRY_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.COUNTRY_SEQUENCE)
    private Long id;
    @NotNull
    private String name;
}
