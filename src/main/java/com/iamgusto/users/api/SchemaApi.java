package com.iamgusto.users.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.api.utils.HttpMessages;
import com.iamgusto.users.api.models.ListResponse;
import com.iamgusto.users.data.Schema;
import com.iamgusto.users.service.Page;
import com.iamgusto.users.service.Schemas;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("v2/Schemas")
public class SchemaApi {

  Schemas schemas;

  HttpMessages messages;
  @Inject
  ObjectMapper objectMapper;

  @GET
  @Path("/")
  public ListResponse getMany(@QueryParam("startIndex") int start, @QueryParam("count") int count,
      @QueryParam("filter") String filter, @QueryParam("sort") String sort) {
    Page<Schema> schemaPage = schemas.get(start, count);
    return new ListResponse(schemaPage.getTotalResults(),
        schemaPage.getStartIndex(),
        schemaPage.getItemsPerPage(),
        objectMapper.valueToTree(schemaPage.getItems()));
  }

  @GET
  @Path("/{id}")
  public Schema get(@PathParam("id") URI uri) {
    return schemas.get(uri)
        .orElseThrow(() -> new NotFoundException(messages.of("http.Schemas.notFound", uri)));
  }
}
