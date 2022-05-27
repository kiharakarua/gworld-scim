package com.iamgusto.users.api;

import com.iamgusto.users.api.utils.HttpMessages;
import com.iamgusto.users.model.BaseScimResource;
import com.iamgusto.users.data.ResourceType;
import com.iamgusto.users.service.ResourceTypes;
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

@Path("v2/ResourceTypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceTypeApi {

  ResourceTypes resourceTypes;

  HttpMessages messages;

  @GET
  @Path("/")
  public BaseScimResource getMany(@PathParam("name") String name) {
    return resourceTypes.fetch(name)
        .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
  }

  @GET
  @Path("/{name}")
  public BaseScimResource get(@PathParam("name") String name) {
    return resourceTypes.fetch(name)
        .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
  }

  @POST
  public ResourceType post(ResourceType resourceType) {
    try {
      return resourceTypes.create(resourceType);
    } catch (ExistsException e) {
      throw new BadRequestException();
    }
  }

  @PUT
  @Path("/{name}")
  public ResourceType put(@PathParam("name") String name, ResourceType resourceType) {
    try {
      return resourceTypes.create(resourceType);
    } catch (ExistsException e) {
      throw new BadRequestException();
    }
  }

  @PATCH
  @Path("/{name}")
  public ResourceType patch(@PathParam("name") String name, ResourceType resourceType) {
    try {
      return resourceTypes.create(resourceType);
    } catch (ExistsException e) {
      throw new BadRequestException();
    }
  }


  @DELETE
  @Path("/{name}")
  public ResourceType delete(@PathParam("name") String name) {
    return resourceTypes.fetch(name)
        .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
  }

  public static class ExistsException extends Exception {

  }
}
