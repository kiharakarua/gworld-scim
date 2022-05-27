package com.iamgusto.users.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.iamgusto.users.model.Attribute;
import com.iamgusto.users.model.BaseScimResource;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "scim_schemas")
public class Schema extends BaseScimResource {

  private String name;
  private String description;
  private List<Attribute> attributes;

  @JsonCreator
  public Schema(String id, String name, String description, List<Attribute> attributes, Meta meta) {
    super(id, meta);
    this.name = name;
    this.description = description;
    this.attributes = attributes;
  }

  public Schema() {
    super();
  }

  @Id
  @Column(name = "ID")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getExternalId() {
    return super.getExternalId();
  }

  @Override
  public void setExternalId(String externalId) {
    super.setExternalId(externalId);
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
  public void setMeta(Meta meta) {
    super.setMeta(meta);
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

  @Column(name = "schema_name")
  public String getName() {
    return name;
  }

  @Column(name = "description", columnDefinition = "text")
  public String getDescription() {
    return description;
  }

  @OneToMany(cascade = CascadeType.ALL)
  public List<Attribute> getAttributes() {
    return attributes;
  }

  @Override
  @Transient
  public Set<String> getSchemas() {
    return Set.of("urn:ietf:params:scim:schemas:core:2.0:Schema");
  }

}
