package br.com.orion.school.model.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * PageableResponseWrapper
 */
public class PageableResponseWrapper<T> extends PageImpl<T> {

    private static final long serialVersionUID = 1L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageableResponseWrapper(
        @JsonProperty("content") List<T> content, 
        @JsonProperty("number") int number,
        @JsonProperty("size") int size, 
        @JsonProperty("totalElements") Long totalElements,
        @JsonProperty("pageable") JsonNode pageable, 
        @JsonProperty("last") boolean last,
        @JsonProperty("totalPages") int totalPages,
        @JsonProperty("first") boolean first, 
        @JsonProperty("numberOfElements") int numberOfElements,
        @JsonProperty("sort") @JsonDeserialize(using = CustomSortDeserializerWrapper.class) Sort sort){

        super(content, PageRequest.of(number, size, sort), totalElements);
    }

}