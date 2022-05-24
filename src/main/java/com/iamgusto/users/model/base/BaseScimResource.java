package com.iamgusto.users.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public abstract class BaseScimResource implements ScimResource {

  @JsonProperty("_id")
  protected final String id;
  protected String externalId;
  protected Meta meta;

  public BaseScimResource(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  @Override
  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseScimResource that = (BaseScimResource) o;
    return id.equals(that.id) && externalId.equals(that.externalId) && meta.equals(that.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, externalId, meta);
  }
}
