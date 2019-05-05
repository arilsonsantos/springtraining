package br.com.orion.cursospring.error;

import lombok.Getter;
import lombok.Setter;

/**
 * ErrorDetail
 */
@Getter
@Setter
public class ErrorDetails {

    protected String title;
    protected int status;
    protected String detail;
    protected long timestamp;
    protected String developerMessage;

    public static final class Builder {
        private static final ErrorDetails objBuilder = new ErrorDetails();

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
        public ErrorDetails build() {
            return objBuilder;
        }
    }

}