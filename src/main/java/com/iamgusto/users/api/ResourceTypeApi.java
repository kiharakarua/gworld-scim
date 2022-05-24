package com.iamgusto.users.api;

import com.iamgusto.users.model.ResourceType;
import com.iamgusto.users.model.Schema;
import com.iamgusto.users.service.ResourceTypes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("v2/ResourceTypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceTypeApi {
    ResourceTypes resourceTypes;

    HttpMessages messages;

    @GET
    @Path("/")
    public ResourceType get(@PathParam("name") String name) {
        return resourceTypes.fetch(name)
                .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
    }
    @GET
    @Path("/{name}")
    public ResourceType get(@PathParam("name") String name) {
        return resourceTypes.fetch(name)
                .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
    }

    @POST
    public ResourceType post(ResourceType resourceType){
        try {
            return resourceTypes.create(resourceType);
        } catch (ExistsException e){
            throw
        }
    }

    @PUT
    @Path("/{name}")
    public ResourceType put(@PathParam("name") String name, ResourceType resourceType){
        try {
            return resourceTypes.create(resourceType);
        } catch (ExistsException e){
            throw
        }
    }

    @PATCH
    @Path("/{name}")
    public ResourceType patch(@PathParam("name") String name, ResourceType resourceType){
        try {
            return resourceTypes.create(resourceType);
        } catch (ExistsException e){
            throw
        }
    }


    @GET
    @Path("/{name}")
    public ResourceType delete(@PathParam("name") String name) {
        return resourceTypes.fetch(name)
                .orElseThrow(() -> new NotFoundException(messages.of("http.ResourceTypes.notFound", name)));
    }
}
