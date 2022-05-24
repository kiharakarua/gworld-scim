package com.iamgusto.users.service.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.model.Schema;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@QuarkusTest
class SchemaTest {

    @Inject
    ObjectMapper mapper;

    @Test
    void serialisation() throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("resource_type_schemas.json");
        TypeReference<List<Schema>> type = new TypeReference<List<Schema>>() {
        };
        List<Schema> schemas = mapper.readValue(stream, type);
        System.out.println("schemas = " + schemas);
    }
}