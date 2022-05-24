package com.iamgusto.users.model.base.serviceprovider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    void deSerialisation_resourceTypes() throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
            "base_resource_schemas.json");
        TypeReference<List<Schema>> type = new TypeReference<List<Schema>>() {
        };
        List<Schema> schemas = mapper.readValue(stream, type);
        System.out.println("schemas = " + schemas);
    }

    @Test
    void deSerialisation_serviceProviderTypes() throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("service_provider_schemas.json");
        TypeReference<List<Schema>> type = new TypeReference<List<Schema>>() {
        };
        List<Schema> schemas = mapper.readValue(stream, type);
        System.out.println("schemas = " + schemas);
    }
}