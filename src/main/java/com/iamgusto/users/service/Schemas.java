package com.iamgusto.users.service;

import com.iamgusto.users.model.base.Attribute;
import com.iamgusto.users.model.base.serviceprovider.Schema;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Schemas {

  private static final List<Attribute> userAttributes = new ArrayList<>();
  public static final Schema USER = new Schema("urn:ietf:params:scim:schemas:core:2.0:User",
      "User",
      "The user schema as defined by scim2",
      userAttributes);

  static {
    userAttributes.addAll(CommonAttributes.all());
  }

  public Optional<Schema> get(URI uri) {
    return Optional.empty();
  }

  public Page<Schema> get() {
    return null;
  }
}
