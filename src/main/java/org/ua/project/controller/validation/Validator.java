package org.ua.project.controller.validation;

import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.dto.RegistrationData;

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

    public ValidationResult validateRegData(RegistrationData data) {
        ValidationResult result = new ValidationResult();

        if (!validate(data.getFirstName(), ValidatedParameter.FIRST_NAME)) {
            result.setSuccessful(false);
            result.addInvalidParameter(Parameter.FIRST_NAME);
        }

        if (!validate(data.getLastName(), ValidatedParameter.LAST_NAME)) {
            result.setSuccessful(false);
            result.addInvalidParameter(Parameter.LAST_NAME);
        }

        if (!validate(data.getLogin(), ValidatedParameter.LOGIN)) {
            result.setSuccessful(false);
            result.addInvalidParameter(Parameter.LOGIN);
        }

        if (!validate(data.getPassword(), ValidatedParameter.PASSWORD)) {
            result.setSuccessful(false);
            result.addInvalidParameter(Parameter.PASSWORD);
        }

        return result;
    }

    private boolean validate(String value, ValidatedParameter parameter) {
        int minLength = Integer.parseInt(properties.getProperty(parameter.getMinLengthProperty()));
        int maxLength = Integer.parseInt(properties.getProperty(parameter.getMaxLengthProperty()));
        String regex = properties.getProperty(parameter.getRegexProperty());

        if (value.length() < minLength || value.length() > maxLength) {
            return false;
        }

        return value.matches(regex);
    }

    private enum ValidatedParameter {
        LOGIN(Parameter.LOGIN),
        PASSWORD(Parameter.PASSWORD),
        FIRST_NAME(Parameter.PASSWORD),
        LAST_NAME(Parameter.LAST_NAME);

        String parameterName;

        ValidatedParameter (String parameterName) {
            this.parameterName = parameterName;
        }

        String getMinLengthProperty() {
            return parameterName + ".minLength";
        }

        String getMaxLengthProperty() {
            return parameterName + ".maxLength";
        }

        String getRegexProperty() {
            return parameterName + ".regex";
        }
    }
}
