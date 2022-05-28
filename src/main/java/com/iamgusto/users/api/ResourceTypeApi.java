package com.iamgusto.users.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.api.models.ListResponse;
import com.iamgusto.users.api.models.Type;
import com.iamgusto.users.api.utils.HttpMessages;
import com.iamgusto.users.api.utils.NodeWriter;
import com.iamgusto.users.data.ResourceType;
import com.iamgusto.users.data.ResourceType.SchemaExtension;
import com.iamgusto.users.data.Schema;
import com.iamgusto.users.data.SchemaRepo;
import com.iamgusto.users.model.BaseScimResource;
import com.iamgusto.users.service.ResourceTypesSvc;
import com.iamgusto.users.service.ResourceTypesSvc.PageInfo;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("v2/ResourceTypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceTypeApi {

  @Inject
  ResourceTypesSvc resourceTypesSvc;

  @Inject
  HttpMessages messages;

  @Inject
  ObjectMapper mapper;

  @Inject
  SchemaRepo schemaRepo;

  @GET
  @Path("/")
  public ListResponse getMany(@QueryParam("startIndex") int start,
      @QueryParam("count") int count,
      @QueryParam("filter") String filter, @QueryParam("sort") String sort) {
    Schema schema = schemaRepo.findById("ResourceType");
    Collection<SchemaExtension> schemaExtensions = Collections.emptyList();
    final Filters filters;
    try {
      filters = Filters.parse(filter, schema, schemaExtensions);
    } catch (Exception e) {
      throw new BadRequestException(e);
    }
    final Sorting sorter;
    try {
      sorter = Sorting.parse(filter, schema);
    } catch (Exception e) {
      throw new BadRequestException(e);
    }
    PageInfo<ResourceType> pageInfo = resourceTypesSvc.fetchAll(start, count, filters,
        sorter);
    NodeWriter nodeWriter = new NodeWriter(mapper);
    return new ListResponse(pageInfo.totalCount(),
        pageInfo.startIndex(),
        pageInfo.requestedItems(),
        nodeWriter.write(pageInfo.items(), schema, schemaExtensions));
  }

  @GET
  @Path("/{name}")
  public BaseScimResource get(@PathParam("name") String name) {
    return resourceTypesSvc.fetch(name)
        .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
  }

  @POST
  public ResourceType post(Type.Resource resourceType) {
    try {
      return resourceTypesSvc.create(resourceType);
    } catch (ExistsException e) {
      throw new BadRequestException(e);
    }
  }

  @PUT
  @Path("/{name}")
  public ResourceType put(@PathParam("name") String name, Type.Resource resourceType) {
    try {
      return resourceTypesSvc.create(resourceType);
    } catch (Exception e) {
      throw new BadRequestException(e);
    }
  }

  @PATCH
  @Path("/{name}")
  public ResourceType patch(@PathParam("name") String name, Type.Resource resourceType) {
    try {
      return resourceTypesSvc.create(resourceType);
    } catch (Exception e) {
      throw new BadRequestException(e);
    }
  }


  @DELETE
  @Path("/{name}")
  public ResourceType delete(@PathParam("name") String name) {
    return resourceTypesSvc.fetch(name)
        .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
  }

  public static class ExistsException extends Exception {

  }
}
