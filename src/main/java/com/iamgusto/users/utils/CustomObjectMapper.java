package com.iamgusto.users.utils;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.quarkus.jackson.ObjectMapperCustomizer;
import javax.enterprise.inject.Instance;
import javax.inject.Singleton;

public class CustomObjectMapper {

  // Replaces the CDI producer for ObjectMapper built into Quarkus
  @Singleton
  ObjectMapper objectMapper(Instance<ObjectMapperCustomizer> customizers) {
    ObjectMapper mapper = JsonMapper.builder()
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .build(); // Custom `ObjectMapper`

    // Apply all ObjectMapperCustomizer beans (incl. Quarkus)
    for (ObjectMapperCustomizer customizer : customizers) {
      customizer.customize(mapper);
    }

    return mapper;
  }
}