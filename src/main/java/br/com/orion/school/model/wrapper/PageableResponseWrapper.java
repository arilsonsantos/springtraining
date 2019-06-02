package br.com.orion.school.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * PageableResponseWrapper
 */
public class PageableResponseWrapper<T> extends PageImpl<T> {

    private static final long serialVersionUID = 1L;

    public PageableResponseWrapper(
        @JsonProperty("content") List<T> content, 
        @JsonProperty("number") int number,
        @JsonProperty("size") int size, 
        @JsonProperty("totalElements") Long totalElements,
        @JsonProperty("pageable") JsonNode pageable, 
        @JsonProperty("last") boolean last,
        @JsonProperty("totalPages") int totalPages,
        @JsonProperty("first") boolean first, 
        @JsonProperty("numberOfElements") int numberOfElements){

        super(content, PageRequest.of(number, size), totalElements);
    }

    public PageableResponseWrapper() {
        super(new ArrayList<>());
    }

}