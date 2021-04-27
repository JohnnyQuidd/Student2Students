package com.example.student2students.service;

import com.example.student2students.dto.EmailDTO;
import com.example.student2students.model.Email;
import com.example.student2students.model.EmailStatus;
import com.example.student2students.repository.EmailRepository;
import com.example.student2students.util.CustomEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    private final EmailRepository emailRepository;
    private final CustomEmailSender customEmailSender;
    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(EmailRepository emailRepository,
                        CustomEmailSender customEmailSender) {
        this.emailRepository = emailRepository;
        this.customEmailSender = customEmailSender;
    }

    public ResponseEntity<?> sendActivationEmail(EmailDTO emailDTO) {
        Email email = Email.builder()
                .receiverEmail(emailDTO.getReceiverEmail())
                .receiverFirstName(emailDTO.getReceiverFirstName())
                .senderEmail("xmlwebservices2020@gmail.com")
                .subject(emailDTO.getSubject())
                .content(emailDTO.getContent())
                .dateTime(LocalDateTime.now())
                .activationLink(emailDTO.getActivationLink())
                .build();

        String formattedText = buildEmail(email.getReceiverFirstName(), emailDTO.getActivationLink());

        try {
            ResponseEntity<?> response = customEmailSender.sendMail(new String[]{email.getReceiverEmail()}, email.getSubject(), formattedText);
            if(response.getStatusCode().equals(HttpStatus.CREATED)) {
                email.setEmailStatus(EmailStatus.SENT_SUCCESSFULLY);
            }
            else {
                email.setEmailStatus(EmailStatus.ERROR_SENDING);
            }
            emailRepository.save(email);
            return response;

        } catch (Exception e) {
            logger.error("Couldn't persist email to database ", email);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<?> sendCommentingEmail(EmailDTO emailDTO) {
        Email email = Email.builder()
                .receiverEmail(emailDTO.getReceiverEmail())
                .receiverFirstName(emailDTO.getReceiverFirstName())
                .senderEmail("xmlwebservices2020@gmail.com")
                .subject(emailDTO.getSubject())
                .content(emailDTO.getContent())
                .dateTime(LocalDateTime.now())
                .commentingUsername(emailDTO.getStudentCommenting())
                .build();

        String formattedText = "<body>" +
                "<h1> Hello, " + email.getReceiverFirstName() + "</h1>" +
                "<p> Your post has been noticed by other students. A student " + email.getCommentingUsername() + " " +
                " commented your post! </p>" +
                "<a href=\"http://localhost:3000/login\"> Log in for more details </a>" +
                "</body>";

        try {
            ResponseEntity<?> response = customEmailSender.sendMail(new String[]{email.getReceiverEmail()}, email.getSubject(), formattedText);
            if(response.getStatusCode().equals(HttpStatus.CREATED)) {
                email.setEmailStatus(EmailStatus.SENT_SUCCESSFULLY);
            }
            else {
                email.setEmailStatus(EmailStatus.ERROR_SENDING);
            }
            emailRepository.save(email);
            return response;

        } catch (Exception e) {
            logger.error("Couldn't persist email to database ", email);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


}
