package com.iamgusto.users.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.data.ResourceType;
import com.iamgusto.users.data.Schema;
import com.iamgusto.users.data.WriteException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class Provisioner {

  private final TypeReference<List<Schema>> SchemaTypeRef = new TypeReference<>() {
  };
  private final TypeReference<List<ResourceType>> ResourceTypeRef = new TypeReference<>() {
  };
  @Inject
  ApplicationSetup writer;
  @Inject
  ObjectMapper objectMapper;

  @Transactional
  public void provision(Collection<ResourceType> resourceTypesCollection) throws WriteException {
    writer.storeServiceSchemas();

    try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("base_resource_schemas.json")) {
      List<Schema> schemas = objectMapper.readValue(resourceAsStream, SchemaTypeRef);
      writer.storeSchemas(schemas);
    } catch (IOException e) {
      e.printStackTrace();
      throw new WriteException(e);
    }

    final Set<ResourceType> resourceTypes = new HashSet<>(resourceTypesCollection);

    try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("base_resource_types.json")) {
      List<ResourceType> basicResourceTypes = objectMapper.readValue(resourceAsStream,
          ResourceTypeRef);
      resourceTypes.addAll(basicResourceTypes);
    } catch (IOException e) {
      e.printStackTrace();
      throw new WriteException(e);
    }
    writer.storeResourceTypes(resourceTypes);
  }
}
