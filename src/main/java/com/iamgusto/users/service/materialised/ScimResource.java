package com.iamgusto.users.service.materialised;

import java.time.ZonedDateTime;

public class ScimResource {

  protected String id;
  protected String externalId;
  //REadOnly
  protected Meta meta;

  private static class Meta {

    private final String resourceType;
    private final ZonedDateTime created;
    private final ZonedDateTime lastModified;

    private final String location;
    private final Version version;

    private Meta(String resourceType, ZonedDateTime created, ZonedDateTime lastModified,
        String location, Version version) {
      this.resourceType = resourceType;
      this.created = created;
      this.lastModified = lastModified;
      this.location = location;
      this.version = version;
    }
  }

  private static class Version {

  }
}
