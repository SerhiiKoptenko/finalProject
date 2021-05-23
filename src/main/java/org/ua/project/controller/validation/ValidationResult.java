package org.ua.project.controller.validation;

import java.util.ArrayList;
import java.util.List;

public final class ValidationResult {
    private boolean isSuccessful = true;
    private final List<String> invalidParameters = new ArrayList<>();

    protected ValidationResult(){};

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public List<String> getInvalidParameters() {
        return invalidParameters;
    }

    public void addInvalidParameter(String parameter) {
       invalidParameters.add(parameter);
    }
}
