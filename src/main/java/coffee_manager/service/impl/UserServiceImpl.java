package coffee_manager.service.impl;

import coffee_manager.config.Constants;
import coffee_manager.dto.DataMailDTO;
import coffee_manager.dto.request.RegisterRequest;
import coffee_manager.exception.NotFoundException;
import coffee_manager.service.MailService;
import coffee_manager.service.UserService;
import coffee_manager.service.utils.DataUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MailService mailService;

    @Override
    public Boolean createUser(RegisterRequest request) {
        try {
            DataMailDTO dataMailDTO = new DataMailDTO();
            dataMailDTO.setTo(request.getEmail());
            dataMailDTO.setSubject(Constants.SUBJECT_MAIL);
            Map<String, Object> props = new HashMap<>();
            props.put("userName", request.getUserFullName());
            props.put("email", request.getEmail());
            props.put("birthDate", request.getUserBirthdate());
            props.put("password", DataUtils.generatePwd(6));
            dataMailDTO.setProps(props);
            mailService.sendHtmlMail(dataMailDTO, "templates");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }
}
