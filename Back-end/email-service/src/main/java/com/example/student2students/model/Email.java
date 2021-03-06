package com.example.student2students.model;

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
@ToString
public class Email {
    @Id
    @SequenceGenerator(name = "email_sequence", sequenceName = "email_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_sequence")
    private Long id;
    @NotNull
    private String subject;
    @NotNull
    private String senderEmail;
    @NotNull
    private String receiverEmail;
    @NotNull
    private String receiverFirstName;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus;
    private String activationLink;
    @Column(name="content", columnDefinition="LONGTEXT")
    private String content;
    private String commentingUsername;
}
