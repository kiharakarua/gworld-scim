package com.iamgusto.users.service.base;

import java.util.List;

public class ResourceType {

    private final String id;
    private final String name;
    private final String description;
    private final String endpoint;

    //URI
    private final String schema;

    private final List<SchemaExtension> schemaExtension;

    public ResourceType(String id, String name, String description, String endpoint, String schema, List<SchemaExtension> schemaExtension) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endpoint = endpoint;
        this.schema = schema;
        this.schemaExtension = schemaExtension;
    }

    public String getName() {
        return name;
    }

    public String getSchema() {
        return schema;
    }

}
