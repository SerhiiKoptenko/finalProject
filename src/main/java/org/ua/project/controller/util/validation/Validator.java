package org.ua.project.controller.util.validation;

import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.dto.RegistrationData;
import org.ua.project.model.dto.SignInData;
import org.ua.project.model.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Validator {
 private static final String VALIDATION_PROPERTIES = "validation.properties";
    private final Properties properties = new Properties();

    private static volatile Validator instance;

    private Validator() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = loader.getResourceAsStream(VALIDATION_PROPERTIES)) {
            properties.load(stream);
        }
    }

    public static Validator getInstance() throws IOException {
        synchronized (Validator.class) {
            if (instance == null) {
                instance = new Validator();
            }
        }
        return instance;
    }

    public ValidationResult validateUser(User user) {
        ValidationResult result = new ValidationResult();

        validateParameter(user.getFirstName(), Parameter.FIRST_NAME, result);
        validateParameter(user.getLastName(), Parameter.LAST_NAME, result);
        validateParameter(user.getLogin(), Parameter.LOGIN, result);
        validateParameter(user.getPassword(), Parameter.PASSWORD, result);
        return result;
    }

    public ValidationResult validateSignInData(String login, String password) {
        ValidationResult result = new ValidationResult();

        validateParameter(login, Parameter.LOGIN, result);
        validateParameter(password, Parameter.PASSWORD, result);
        return result;
    }

    private void validateParameter(String value, Parameter parameter, ValidationResult result) {
        String minLengthProp = properties.getProperty(parameter.getValue() + ".minLength");
        String maxLengthProp = properties.getProperty(parameter.getValue() + ".maxLength");
        int minLength = Integer.parseInt(minLengthProp);
        int maxLength = Integer.parseInt(maxLengthProp);
        String regex = properties.getProperty(parameter.getValue() + ".regex");

        if (value.length() < minLength || value.length() > maxLength
                || !value.matches(regex)) {
            result.setSuccessful(false);
            result.addInvalidParameter(parameter);
        }
    }
}
