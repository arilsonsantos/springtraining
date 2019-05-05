package br.com.orion.cursospring.error;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * ValidationErrorDetails
 */
@Getter
@Setter
public class ValidationErrorDetails extends ErrorDetails {

    private Map<String, String> errors;

    public static final class Builder {
        private static final ValidationErrorDetails objBuilder = new ValidationErrorDetails();

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            objBuilder.title = title;
            return this;
        }

        public Builder status(int status) {
            objBuilder.status = status;
            return this;
        }

        public Builder detail(String detail) {
            objBuilder.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            objBuilder.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            objBuilder.developerMessage = developerMessage;
            return this;
        }


        public Builder addError(Map<String, String> fieldsErrors) {
            objBuilder.errors = fieldsErrors;
            return this;
        }

        public ValidationErrorDetails build() {
            return objBuilder;
        }

    }

}