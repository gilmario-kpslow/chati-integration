package br.gov.ce.sefaz.chati.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

/**
 *
 * @author gilmario
 */
@Singleton
public class Configurer implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
//        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
//        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        objectMapper.registerModule(new JavaTimeModule());

    }

}
