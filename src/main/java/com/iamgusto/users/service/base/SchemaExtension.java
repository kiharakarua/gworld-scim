package com.iamgusto.users.service.base;

public class SchemaExtension {
    //URI
    private final String schema;
    private final boolean required;

    private SchemaExtension(String schema, boolean required) {
        this.schema = schema;
        this.required = required;
    }
}
