package com.iamgusto.users.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ResourceType extends CoreResource.BaseCoreResource {

    private final String name;
    private final String description;
    private final String endpoint;

    //URI
    private final String schema;

    private final List<SchemaExtension> schemaExtension;

    public ResourceType(String name, String description, String endpoint, String schema, List<SchemaExtension> schemaExtension) {
        super(name);
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

    public String getDescription() {
        return description;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public List<SchemaExtension> getExtension() {
        return schemaExtension;
    }

    public static class SchemaExtension {
        //URI
        private final String schema;
        private final boolean required;

        private SchemaExtension(String schema, boolean required) {
            this.schema = schema;
            this.required = required;
        }

        public String getSchema() {
            return schema;
        }

        public boolean isRequired() {
            return required;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SchemaExtension schemaExtension = (SchemaExtension) o;
            return required == schemaExtension.required && Objects.equals(schema, schemaExtension.schema);
        }

        @Override
        public int hashCode() {
            return Objects.hash(schema, required);
        }
    }


    @Override
    public Set<String> getSchemas() {
        return Set.of("urn:ietf:params:scim:schemas:core:2.0:ResourceType");
    }
}
