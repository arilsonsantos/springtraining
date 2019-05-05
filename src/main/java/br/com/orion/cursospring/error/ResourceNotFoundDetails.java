package br.com.orion.cursospring.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ResourceNotFoundDetails
 */
@Getter
@NoArgsConstructor
public class ResourceNotFoundDetails extends ErrorDetails{

    public static final class Builder {
        private static ResourceNotFoundDetails build = new ResourceNotFoundDetails();

        public static Builder newBuider() {
            return new Builder();
        }

        public Builder title(String title) {
            build.title = title;
            return this;
        }

        public Builder status(int status) {
            build.status = status;
            return this;
        }

        public Builder detail(String detail) {
            build.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            build.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            build.developerMessage = developerMessage;
            return this;
        }

        public ResourceNotFoundDetails build() {
            return build;
        }

    }

}