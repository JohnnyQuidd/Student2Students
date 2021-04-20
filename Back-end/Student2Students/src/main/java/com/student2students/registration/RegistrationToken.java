package com.student2students.registration;

import com.student2students.constants.SequenceConstants;
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
public class RegistrationToken {
    @Id
    @SequenceGenerator(name = SequenceConstants.REGISTRATION_TOKEN, sequenceName = SequenceConstants.REGISTRATION_TOKEN, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SequenceConstants.REGISTRATION_TOKEN)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String token;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime expiresAt;
    private boolean confirmed;
}
