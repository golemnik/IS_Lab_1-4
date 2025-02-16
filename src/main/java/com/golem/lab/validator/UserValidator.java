package com.golem.lab.validator;

import com.golem.lab.model.Client;
import com.golem.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator{
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
}
    @Override
    public void validate(Object o, Errors errors) {
     Client client = (Client) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (client.getUsername().length() < 2 || client.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(client.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (client.getPassword().length() < 2 || client.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!client.getPasswordConfirm().equals(client.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }   
    }
    
}
