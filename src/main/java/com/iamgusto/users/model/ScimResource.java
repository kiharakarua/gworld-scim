package com.iamgusto.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.iamgusto.users.utils.TimeUtils;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

public interface ScimResource extends ScimObject {

  String getId();

  String getExternalId();

  Meta getMeta();

  @Embeddable
  public static class Meta {

    @Column(name = "resource_type")
    private String resourceType;
    @Column(name = "location")
    private String location;
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    @JsonCreator
    public Meta(String resourceType, String location, LocalDateTime created,
        LocalDateTime lastModifiedAt) {
      this.resourceType = resourceType;
      this.location = location;
      this.created = Objects.requireNonNullElse(created, TimeUtils.now());
      this.lastModifiedAt = Objects.requireNonNullElse(lastModifiedAt, TimeUtils.now());
    }

    public Meta(String resourceType, String location) {
      this.resourceType = resourceType;
      this.location = location;
      this.created = TimeUtils.now();
    }

    public Meta() {
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
