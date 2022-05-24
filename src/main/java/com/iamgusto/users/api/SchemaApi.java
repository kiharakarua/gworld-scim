package com.iamgusto.users.api;

import com.iamgusto.users.model.Schema;
import com.iamgusto.users.service.Schemas;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import java.net.URI;

@Path("v2/Schemas")
public class SchemaApi {
    Schemas schemas;

    HttpMessages messages;

    @GET
    @Path("/{id}")
    public Schema get(URI uri) {
        schemas.get(uri)
                .orElseThrow(() -> new NotFoundException(messages.of("http.Schemas.notFound", uri)));
    }
}
