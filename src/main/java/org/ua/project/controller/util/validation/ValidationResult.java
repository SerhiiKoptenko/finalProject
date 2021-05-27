package org.ua.project.controller.util.validation;

import org.ua.project.controller.constants.Parameter;

import java.util.ArrayList;
import java.util.List;

public final class ValidationResult {
    private boolean isSuccessful = true;
    private final List<Parameter> invalidParameters = new ArrayList<>();

    protected ValidationResult(){};

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public List<Parameter> getInvalidParameters() {
        return invalidParameters;
    }

    public void addInvalidParameter(Parameter parameter) {
       invalidParameters.add(parameter);
    }

    public String  getInvalidParametersString() {
        StringBuilder sb = new StringBuilder();
        for (Parameter parameter : invalidParameters) {
            sb.append("&invalid_").append(parameter.getValue());
        }
        return sb.toString();
    }
}
