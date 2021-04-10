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
@EqualsAndHashCode
@Builder
public class Address {
    @Id
    @SequenceGenerator(name = SequenceConstants.ADDRESS_SEQUENCE, sequenceName = SequenceConstants.ADDRESS_SEQUENCE,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.ADDRESS_SEQUENCE)
    private Long id;
    @NotNull
    private String city;
    @NotNull
    private String streetName;
    @NotNull
    private String streetNumber;
}
