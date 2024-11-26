package coffee_manager.service;

import coffee_manager.dto.DataMailDTO;
import jakarta.mail.MessagingException;


public interface MailService {
    void sendHtmlMail (DataMailDTO mailDTO, String templateMail) throws MessagingException;
}
