package com.iamgusto.users.model;

import java.util.List;
import java.util.Set;

public class Schema extends CoreResource.BaseCoreResource {

    //URI
    private final String id;
    private final String name;
    private final String description;
    private final List<Attribute> attributes;

    public Schema(String id, String name, String description, List<Attribute> attributes) {
        super(id);
        this.id = id;
        this.name = name;
        this.description = description;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public Set<String> getSchemas() {
        return Set.of("urn:ietf:params:scim:schemas:core:2.0:Schema");
    }
}
