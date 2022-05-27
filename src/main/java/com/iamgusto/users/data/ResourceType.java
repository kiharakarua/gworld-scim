package com.iamgusto.users.data;

import com.iamgusto.users.model.BaseScimResource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "resource_types")
public class ResourceType extends BaseScimResource {

  private String name;

  private String description;

  private String endpoint;

  //URI
  private String schema;

  private List<SchemaExtension> schemaExtensions;

  public ResourceType(String name, String description, String endpoint, String schema,
      List<SchemaExtension> schemaExtensions, Meta meta) {
    super(name, meta);
    this.name = name;
    this.description = description;
    this.endpoint = endpoint;
    this.schema = schema;
    this.schemaExtensions = schemaExtensions;
  }

  public ResourceType() {

  }


  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public void setSchemaExtensions(
      List<SchemaExtension> schemaExtension) {
    this.schemaExtensions = schemaExtension;
  }

  @Id
  @Column(name = "resource_type_name")
  public String getName() {
    return name;
  }

  @Column(name = "resource_type_schema")
  public String getSchema() {
    return schema;
  }

  @Column(name = "description", columnDefinition = "text")
  public String getDescription() {
    return description;
  }

  @Column(name = "endpoint")
  public String getEndpoint() {
    return endpoint;
  }

  @OneToMany(cascade = CascadeType.ALL)
  public List<SchemaExtension> getSchemaExtensions() {
    return schemaExtensions;
  }

  @Override
  @Column(name = "external_id")
  public String getExternalId() {
    return super.getExternalId();
  }

  @Override
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "created", column = @Column(nullable = false, name = "created_on")),
      @AttributeOverride(name = "lastModifiedAt", column = @Column(nullable = false, name = "last_modified_at"))
  })
  public Meta getMeta() {
    return super.getMeta();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ResourceType that = (ResourceType) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }

  @Override
  @Transient
  public Set<String> getSchemas() {
    return Set.of("urn:ietf:params:scim:schemas:core:2.0:ResourceType");
  }

  @Entity
  @Table(name = "schema_extension")
  public static class SchemaExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;
    //URI
    @Column(name = "schema_uri")
    private String schema;
    @Column(name = "required")
    private boolean required;

    public SchemaExtension() {
    }

    public SchemaExtension(String schema, boolean required) {
      this.schema = schema;
      this.required = required;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public void setSchema(String schema) {
      this.schema = schema;
    }

    public void setRequired(boolean required) {
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
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      SchemaExtension schemaExtension = (SchemaExtension) o;
      return required == schemaExtension.required && Objects.equals(schema, schemaExtension.schema);
    }

    @Override
    public int hashCode() {
      return Objects.hash(schema, required);
    }
  }
}
