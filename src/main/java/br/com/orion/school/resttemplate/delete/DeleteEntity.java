package br.com.orion.school.resttemplate.delete;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.handler.RestResponseErrorHandler;

/**
 * DeleteEntity
 */
public class DeleteEntity {

    public static void main(String[] args) {
        delete();
    }

    public static void delete() {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/orion/api/v1/admin/students").basicAuthentication("maria", "123")
                .errorHandler(new RestResponseErrorHandler())
                .build();


        restTemplate.delete("/{id}", 20L);
        

    }
    
}