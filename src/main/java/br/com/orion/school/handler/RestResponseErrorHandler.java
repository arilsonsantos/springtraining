package br.com.orion.school.handler;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * DefaultResponseErrorHandler
 */
@ControllerAdvice
public class RestResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println("HAS ERROR");
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("HANDLE ERROR");
        System.out.println(IOUtils.toString(response.getBody(), "UTF-8"));
    }

}