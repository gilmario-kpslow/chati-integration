package br.gov.ce.sefaz.chati.utils;

import br.gov.ce.sefaz.chati.pocketbase.PageResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author gilmario
 */
public class JsonConverter {

    private final static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T> List<T> fromJsonList(String json, Class<T> classe) throws IOException {
        if (Objects.isNull(json) || "".equals(json)) {
            return Collections.emptyList();
        }
        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, classe);
        return objectMapper.readValue(json, javaType);
    }

    public static <T> T fromJson(String json, Class<T> classe) throws IOException {
        if (Objects.isNull(json) || "".equals(json)) {
            return null;
        }
        return objectMapper.readValue(json, classe);
    }

    public static PageResponse fromJsonPage(String json, Class classe) throws IOException {
        if (Objects.isNull(json) || "".equals(json)) {

            return null;
        }

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(PageResponse.class, classe);
        return objectMapper.readValue(json, javaType);
    }

    public static String toJson(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

}
