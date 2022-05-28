package com.iamgusto.users.api.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import java.util.Objects;

public interface Type {

  class Resource {

    private String name;

    private String description;

    private String endpoint;

    //URI
    private String schema;

    private List<Extension> schemaExtensions;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getEndpoint() {
      return endpoint;
    }

    public void setEndpoint(String endpoint) {
      this.endpoint = endpoint;
    }

    public String getSchema() {
      return schema;
    }

    public void setSchema(String schema) {
      this.schema = schema;
    }

    public List<Extension> getSchemaExtensions() {
      return schemaExtensions;
    }

    public void setSchemaExtensions(
        List<Extension> schemaExtensions) {
      this.schemaExtensions = schemaExtensions;
    }
  }


  public static class Extension {

    //URI
    private String schema;

    private boolean required;

    public Extension() {
    }


    @JsonCreator
    public Extension(String schema, boolean required) {
      this.schema = schema;
      this.required = required;
    }

    public String getSchema() {
      return schema;
    }

    public void setSchema(String schema) {
      this.schema = schema;
    }

    public boolean isRequired() {
      return required;
    }

    public void setRequired(boolean required) {
      this.required = required;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Extension schemaExtension = (Extension) o;
      return required == schemaExtension.required && Objects.equals(schema, schemaExtension.schema);
    }

    @Override
    public int hashCode() {
      return Objects.hash(schema, required);
    }
  }
}
