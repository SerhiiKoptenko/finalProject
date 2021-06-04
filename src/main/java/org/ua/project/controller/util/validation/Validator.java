package org.ua.project.controller.util.validation;

import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

public class Validator {
 private static final String VALIDATION_PROPERTIES = "validation.properties";
    private final Properties properties = new Properties();

    private static volatile Validator instance;

    private Validator() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = loader.getResourceAsStream(VALIDATION_PROPERTIES)) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Validator getInstance()  {
        if (instance == null) {
            synchronized (Validator.class) {
                if (instance == null) {
                    Validator temp = new Validator();
                    instance = temp;
                }
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

    public ValidationResult validateCourse(Course course) {
        ValidationResult result = new ValidationResult();

        validateParameter(course.getName(), Parameter.COURSE_NAME, result);
        validateCourseDate(course, result);
        return result;
    }

    public ValidationResult validateSignInData(String login, String password) {
        ValidationResult result = new ValidationResult();

        validateParameter(login, Parameter.LOGIN, result);
        validateParameter(password, Parameter.PASSWORD, result);
        return result;
    }

    public void validateParameter(String value, Parameter parameter, ValidationResult result) {
        String minLengthProp = properties.getProperty(parameter.getValue() + ".minLength");
        String maxLengthProp = properties.getProperty(parameter.getValue() + ".maxLength");
        int minLength = Integer.parseInt(minLengthProp);
        int maxLength = Integer.parseInt(maxLengthProp);
        String regex = properties.getProperty(parameter.getValue() + ".regex");

        if (value == null
                || value.length() < minLength
                || value.length() > maxLength
                || !value.matches(regex)) {
            result.setSuccessful(false);
            result.addInvalidParameter(parameter);
        }
    }

    private void validateCourseDate(Course course, ValidationResult validationResult) {
        LocalDate startDate = course.getStartDate();
        LocalDate endDate = course.getEndDate();

        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            validationResult.addInvalidParameter(Parameter.COURSE_START_DATE_OR_END_DATE);
            validationResult.setSuccessful(false);
        }
    }
}
