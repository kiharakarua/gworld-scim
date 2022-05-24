package com.iamgusto.users.model;

import java.time.LocalDateTime;
import java.util.Objects;

public interface CoreResource extends Resource {
    String getId();

    String getExternalId();

    BaseCoreResource.Meta getMeta();

    abstract class BaseCoreResource implements CoreResource {

        protected final String id;
        protected String externalId;
        protected Meta meta;

        public BaseCoreResource(String id) {
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BaseCoreResource that = (BaseCoreResource) o;
            return id.equals(that.id) && externalId.equals(that.externalId) && meta.equals(that.meta);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, externalId, meta);
        }

        public static class Meta {
            private final String resourceType;
            private final String location;
            private final LocalDateTime created;
            private final LocalDateTime lastModifiedAt;

            public Meta(String resourceType, String location, LocalDateTime created, LocalDateTime lastModifiedAt) {
                this.resourceType = resourceType;
                this.location = location;
                this.created = created;
                this.lastModifiedAt = lastModifiedAt;
            }
        }
    }
}
