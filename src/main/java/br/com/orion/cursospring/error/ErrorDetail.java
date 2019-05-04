package br.com.orion.cursospring.error;

import lombok.Getter;
import lombok.Setter;

/**
 * ErrorDetail
 */
@Getter
@Setter
public class ErrorDetail {

    protected String title;
    protected int status;
    protected String detail;
    protected long timestamp;
    protected String developerMessage;
}