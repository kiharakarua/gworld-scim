package com.iamgusto.users.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.data.DataWriter;
import com.iamgusto.users.data.WriteException;
import com.iamgusto.users.model.base.serviceprovider.ResourceType;
import com.iamgusto.users.model.base.serviceprovider.ResourceType.SchemaExtension;
import com.iamgusto.users.model.base.serviceprovider.Schema;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Provisioner {

  private static final ResourceType BasicGroup = new ResourceType("Group",
      "Group", "/Groups", "urn:ietf:params:scim:schemas:core:2.0:Group", Collections.emptyList());

  private static final ResourceType BasicUser = new ResourceType("User",
      "User", "/Users", "urn:ietf:params:scim:schemas:core:2.0:User",
      Collections.singletonList(
          new SchemaExtension("urn:ietf:params:scim:schemas:extension:enterprise:2.0:User", true)));
  @Inject
  DataWriter writer;

  @Inject
  ObjectMapper objectMapper;

  private final TypeReference<List<Schema>> SchemaTypeRef = new TypeReference<>() {
  };

  public void provision(Collection<ResourceType> resourceType) throws WriteException {
    writer.storeServiceSchemas();

    try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("base_resource_schemas.json")) {
      List<Schema> schemas = objectMapper.readValue(resourceAsStream, SchemaTypeRef);
      writer.storeSchemas(schemas);
    } catch (IOException e) {
      throw new WriteException(e);
    }

    final Set<ResourceType> resourceTypes = new HashSet<>(resourceType);
    resourceTypes.add(BasicGroup);
    resourceTypes.add(BasicUser);
    writer.storeResourceTypes(resourceTypes);
  }
}
