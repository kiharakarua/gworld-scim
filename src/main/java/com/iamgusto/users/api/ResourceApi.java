package com.iamgusto.users.api;

import com.iamgusto.users.api.ResourceTypeApi.ExistsException;
import com.iamgusto.users.api.utils.HttpMessages;
import com.iamgusto.users.model.BaseScimResource;
import com.iamgusto.users.data.ResourceType;
import com.iamgusto.users.service.Resources;
import com.iamgusto.users.service.ResourcesProvider;
import java.util.List;
import javax.enterprise.context.RequestScoped;
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
import javax.ws.rs.core.MediaType;

@Path("v2/{endpoint}")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceApi {

  @Inject
  ResourcesProvider resourcesProvider;

  @Inject
  HttpMessages messages;

  @PathParam("endpoint")
  String endpoint;

  @GET
  @Path("/")
  public List<BaseScimResource> getMany(@PathParam("endpoint") String endpoint) {
    Resources resources = resourcesProvider.create(endpoint);
    return resources.fetch();
  }

  @GET
  @Path("/{id}")
  public BaseScimResource get(@PathParam("endpoint") String endpoint, @PathParam("id") String id) {
    Resources resources = resourcesProvider.create(endpoint);
    return resources.fetch(id)
        .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", id)));
  }

  @POST
  public ResourceType post(ResourceType resourceType) {
    try {
      Resources resources = resourcesProvider.create(endpoint);
      return resources.create(resourceType);
    } catch (ExistsException e) {
      throw new BadRequestException();
    }
  }

  @PUT
  @Path("/{id}")
  public ResourceType put(@PathParam("id") String name, ResourceType resourceType) {
    try {
      Resources resources = resourcesProvider.create(endpoint);
      return resources.create(resourceType);
    } catch (ExistsException e) {
      throw new BadRequestException();
    }
  }

  @PATCH
  @Path("/{id}")
  public ResourceType patch(@PathParam("id") String name, ResourceType resourceType) {
    try {
      Resources resources = resourcesProvider.create(endpoint);
      return resources.create(resourceType);
    } catch (ExistsException e) {
      throw new BadRequestException();
    }
  }


  @DELETE
  @Path("/{id}")
  public BaseScimResource delete(@PathParam("id") String name) {
    Resources resources = resourcesProvider.create(endpoint);
    return resources.fetch(name)
        .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
  }
}
