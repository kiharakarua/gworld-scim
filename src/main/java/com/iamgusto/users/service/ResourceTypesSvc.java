package com.iamgusto.users.service;

import com.iamgusto.users.api.Filters;
import com.iamgusto.users.api.ResourceTypeApi.ExistsException;
import com.iamgusto.users.api.Sorting;
import com.iamgusto.users.api.models.Type;
import com.iamgusto.users.api.models.Type.Resource;
import com.iamgusto.users.data.ResourceType;
import com.iamgusto.users.data.ResourceType.SchemaExtension;
import com.iamgusto.users.data.ResourceTypeRepo;
import com.iamgusto.users.data.SchemaRepo;
import com.iamgusto.users.model.ScimResource.Meta;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ResourceTypesSvc {

  @Inject
  ResourceTypeRepo resourceTypeRepo;
  @Inject
  SchemaRepo schemaRepo;

  public Optional<ResourceType> fetch(String name) {
    return resourceTypeRepo.findByIdOptional(name);
  }

  public ResourceType create(Type.Resource resource) throws ExistsException {
    ResourceType resourceType = createResourceImpl(resource);
    resourceTypeRepo.persist(resourceType);
    return resourceType;
  }

  public void create(Collection<Resource> resources) throws ExistsException {
    resourceTypeRepo.persist(resources.stream().map(this::createResourceImpl));
    ;
  }

  private ResourceType createResourceImpl(Resource resource) {
    return new ResourceType(resource.getName(),
        resource.getDescription(),
        resource.getEndpoint(),
        schemaRepo.findById(resource.getSchema()),
        resource.getSchemaExtensions()
            .stream()
            .map(s -> new SchemaExtension(schemaRepo.findById(s.getSchema()), s.isRequired()))
            .collect(Collectors.toList()),
        new Meta("ResourceType", "ResourceTypes/" + resource.getName()));
  }

  public PageInfo<ResourceType> fetchAll(int start, int size, Filters filters, Sorting sorter) {
    long count = resourceTypeRepo.find("SELECT r FROM ResourceType r "
        + " " + filters.toHql()).count();
    List<ResourceType> resourceTypes = resourceTypeRepo.find("SELECT r FROM ResourceType r "
            + " " + filters.toHql()
            + " " + sorter.toHql())
        .range(start, start + size - 1)
        .stream().toList();
    return new PageInfo<>(count, start, size, resourceTypes);
  }

  public record PageInfo<T>(long totalCount, int startIndex, int requestedItems, List<T> items) {

  }
}
