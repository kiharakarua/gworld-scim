package com.iamgusto.users.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.data.ResourceType;
import com.iamgusto.users.data.ResourceTypeRepo;
import com.iamgusto.users.data.Schema;
import com.iamgusto.users.data.SchemaRepo;
import com.iamgusto.users.data.WriteException;
import com.iamgusto.users.model.BaseScimResource;
import com.iamgusto.users.model.ScimResource.Meta;
import com.iamgusto.users.utils.TimeUtils;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationSetup {

  @Inject
  SchemaRepo schemaRepo;
  @Inject
  ResourceTypeRepo resourceTypeRepo;
  @Inject
  ObjectMapper objectMapper;
  private final TypeReference<List<Schema>> SchemaTypeRef = new TypeReference<>() {
  };
  private List<Schema> serviceSchemas;

  @PostConstruct
  public void init() {
    //ToDO Create database schemas
  }

  public void storeSchemas(List<Schema> schemas) {
    schemaRepo.persist(schemas);
  }

  public void createSchema(Schema schema) {
    schemaRepo.persist(schema);
  }

  public void storeServiceSchemas() throws WriteException {
    try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("service_provider_schemas.json");) {
      serviceSchemas = objectMapper.readValue(resourceAsStream, SchemaTypeRef);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WriteException(e);
    }
  }

  private Map<Object, Object> toMap(BaseScimResource schema) {
    Map<Object, Object> map = new HashMap<>(
        objectMapper.convertValue(schema, new TypeReference<Map<String, Object>>() {
        }));
    map.replaceAll((s, o) -> {
      if (o == null) {
        return "";
      }
      return o;
    });
    return map;
  }

  public void markAsInit() {
  }

  public boolean isInit() {
    return true;
  }

  public void storeResourceTypes(Set<ResourceType> resourceTypes) {
    resourceTypeRepo.persist(resourceTypes);
  }

  private <T extends BaseScimResource> T updateMetaCreation(T resourceType) {
    Meta meta;
    if ((meta = resourceType.getMeta()) == null) {
      meta = new Meta("Schema", "/v2/Schemas/" + resourceType.getId());
    }
    meta.setCreated(TimeUtils.now());
    meta.setLastModifiedAt(TimeUtils.now());
    resourceType.setMeta(meta);
    return resourceType;
  }
}
