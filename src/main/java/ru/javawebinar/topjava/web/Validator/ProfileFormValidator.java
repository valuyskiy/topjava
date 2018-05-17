package ru.javawebinar.topjava.web.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Component
public class ProfileFormValidator implements Validator {

    protected final UserService service;

    @Autowired
    public ProfileFormValidator(UserService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            UserTo userTo = (UserTo) target;
            User updatedUser = service.getByEmail(userTo.getEmail());
            if (AuthorizedUser.safeGet() == null || updatedUser.getId() != AuthorizedUser.id()) {
                errors.rejectValue("email", "user.emailExist");
            }
        } catch (NotFoundException e) {
            // ой, не хорошо так делать
        }
    }
}
