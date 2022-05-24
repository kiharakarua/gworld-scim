package com.iamgusto.users.model.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDateTime;
import java.util.Objects;

public interface ScimResource extends ScimObject {

  String getId();

  String getExternalId();

  Meta getMeta();

  public static class Meta {

    private String resourceType;
    private String location;
    private LocalDateTime created;
    private LocalDateTime lastModifiedAt;

    @JsonCreator
    public Meta(String resourceType, String location, LocalDateTime created,
        LocalDateTime lastModifiedAt) {
      this.resourceType = resourceType;
      this.location = location;
      this.created = created;
      this.lastModifiedAt = lastModifiedAt;
    }

    public Meta(String resourceType, String location) {
      this.resourceType = resourceType;
      this.location = location;
    }

    public String getResourceType() {
      return resourceType;
    }

    public void setResourceType(String resourceType) {
      this.resourceType = resourceType;
    }

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }

    public LocalDateTime getCreated() {
      return created;
    }

    public void setCreated(LocalDateTime created) {
      this.created = created;
    }

    public LocalDateTime getLastModifiedAt() {
      return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
      this.lastModifiedAt = lastModifiedAt;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Meta meta = (Meta) o;
      return Objects.equals(resourceType, meta.resourceType) && Objects.equals(
          location, meta.location) && Objects.equals(created, meta.created)
          && Objects.equals(lastModifiedAt, meta.lastModifiedAt);
    }

    @Override
    public int hashCode() {
      return Objects.hash(resourceType, location, created, lastModifiedAt);
    }
  }
}
