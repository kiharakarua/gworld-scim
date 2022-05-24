package com.iamgusto.users.api;

import com.iamgusto.users.model.ResourceType;
import com.iamgusto.users.model.CoreResource;
import com.iamgusto.users.service.Resources;
import com.iamgusto.users.service.ResourcesProvider;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    @Inject
    String endpoint;

    @GET
    @Path("/")
    public List<CoreResource.BaseCoreResource> getMany(@PathParam("endpoint") String endpoint) {
        Resources resources = resourcesProvider.create(endpoint);
        return resources.fetch();
    }

    @GET
    @Path("/{id}")
    public CoreResource.BaseCoreResource get(@PathParam("endpoint") String endpoint, @PathParam("id") String id) {
        Resources resources = resourcesProvider.create(endpoint);
        return resources.fetch(id)
                .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", id)));
    }

    @POST
    public ResourceType post(ResourceType resourceType) {
        try {
            Resources resources = resourcesProvider.create(endpoint);
            return resourceTypes.create(resourceType);
        } catch (ExistsException e) {
            throw
        }
    }

    @PUT
    @Path("/{id}")
    public ResourceType put(@PathParam("name") String name, ResourceType resourceType) {
        try {
            return resourceTypes.create(resourceType);
        } catch (ExistsException e) {
            throw
        }
    }

    @PATCH
    @Path("/{id}")
    public ResourceType patch(@PathParam("name") String name, ResourceType resourceType) {
        try {
            return resourceTypes.create(resourceType);
        } catch (ExistsException e) {
            throw
        }
    }


    @GET
    @Path("/{id}")
    public ResourceType delete(@PathParam("name") String name) {
        return resourceTypes.fetch(name)
                .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
    }
}
