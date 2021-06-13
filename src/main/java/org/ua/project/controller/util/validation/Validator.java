package org.ua.project.controller.util.validation;

import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

/**
 * Class for validation of input parameters.
 */
public class Validator {
    private static final String VALIDATION_PROPERTIES = "validation/validation.properties";
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

    /**
     * Return Validator instance.
     * @return Validator instance.
     */
    public static Validator getInstance() {
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

    /**
     * Validates user entity.
     * @param user - user to be validated.
     * @return validation result.
     */
    public ValidationResult validateUser(User user) {
        ValidationResult result = new ValidationResult();

        validateParameter(user.getFirstName(), Parameter.FIRST_NAME, result);
        validateParameter(user.getLastName(), Parameter.LAST_NAME, result);
        validateParameter(user.getLogin(), Parameter.LOGIN, result);
        validateParameter(user.getPassword(), Parameter.PASSWORD, result);
        return result;
    }

    /**
     * Validates course entity.
     * @param course - course to be validated.
     * @return validation result.
     */
    public ValidationResult validateCourse(Course course) {
        ValidationResult result = new ValidationResult();

        validateParameter(course.getName(), Parameter.COURSE_NAME, result);
        validateCourseDate(course, result);
        return result;
    }

    /**
     * Validates sign in data.
     * @param login - login to be validated.
     * @param password - password to be validated.
     * @return validation result.
     */
    public ValidationResult validateSignInData(String login, String password) {
        ValidationResult result = new ValidationResult();

        validateParameter(login, Parameter.LOGIN, result);
        validateParameter(password, Parameter.PASSWORD, result);
        return result;
    }

    private void validateParameter(String value, Parameter parameter, ValidationResult result) {
        String regex = properties.getProperty(parameter.getValue() + ".regex");

        if (value == null || !value.matches(regex)) {
            result.setSuccessful(false);
            result.addInvalidParameter(parameter);
        }
    }

    private void validateCourseDate(Course course, ValidationResult validationResult) {
        LocalDate startDate = course.getStartDate();
        LocalDate endDate = course.getEndDate();

        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)
                || startDate.isBefore(LocalDate.now())) {
            validationResult.addInvalidParameter(Parameter.COURSE_START_DATE_OR_END_DATE);
            validationResult.setSuccessful(false);
        }
    }
}
