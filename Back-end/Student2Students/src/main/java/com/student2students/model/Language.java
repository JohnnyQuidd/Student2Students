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
public class Language {
    @Id
    @SequenceGenerator(name = SequenceConstants.LANGUAGE_SEQUENCE, sequenceName = SequenceConstants.LANGUAGE_SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.LANGUAGE_SEQUENCE)
    private Long id;

    @NotNull
    private String languageName;

    @NotNull
    private String languageCode;
}
