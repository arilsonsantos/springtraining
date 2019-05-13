package br.com.orion.school.model.wrapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.springframework.data.domain.Sort;

/**
 * CustomSortDeserializerWrapper
 */
public class CustomSortDeserializerWrapper extends JsonDeserializer<Sort> {

    @Override
    public Sort deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

        /*
         * ArrayNode node = jsonParser.getCodec().readTree(jsonParser); Sort.Order[]
         * orders = new Sort.Order[node.size()]; int i = 0; for (JsonNode json : node) {
         * orders[i] = new
         * Sort.Order(Sort.Direction.valueOf(json.get("direction").asText()),
         * json.get("property").asText());
         * 
         * i++; } return Sort.by(orders);
         */
        return null;

    }

}