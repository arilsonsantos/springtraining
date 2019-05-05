package br.com.orion.school.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ResourceNotFoundDetails
 */
@Getter
@NoArgsConstructor
public class ResourceNotFoundDetails extends ErrorDetails{

    public static final class Builder {
        private static ResourceNotFoundDetails objBuilder = new ResourceNotFoundDetails();

        public static Builder newBuider() {
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

        public ResourceNotFoundDetails build() {
            return objBuilder;
        }

    }

}