package com.iamgusto.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iamgusto.users.utils.TimeUtils;
import java.util.Objects;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public abstract class BaseScimResource implements ScimResource {

  protected String id;

  protected String externalId;

  protected Meta meta;

  public BaseScimResource() {
  }

  public BaseScimResource(String id, Meta meta) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  @PrePersist
  public void prePersist() {
    Meta meta1 = getMeta();
    meta1.setCreated(TimeUtils.now());
    meta1.setLastModifiedAt(TimeUtils.now());
  }

  @PreUpdate
  public void preUpdate() {
    Meta meta1 = getMeta();
    if (meta1 != null) {
      meta1.setLastModifiedAt(TimeUtils.now());
    }
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
