package com.iamgusto.users.service;

import com.iamgusto.users.model.Attribute;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CommonAttributes {

  public static final Attribute ID = new SimpleAttr("id",
      Attribute.Type.STRING,
      false,
      " A unique identifier for a SCIM resource as defined by the service provider.",
      true,
      Collections.emptyList(),
      true,
      Attribute.Mutability.readOnly,
      Attribute.Returned.ALWAYS,
      Attribute.Uniqueness.SERVER);

  public static final Attribute EXTERNAL_ID = new SimpleAttr("externalId",
      Attribute.Type.STRING,
      false,
      "A String that is an identifier for the resource as defined by the provisioning client.",
      false,
      Collections.emptyList(),
      true,
      Attribute.Mutability.readWrite,
      Attribute.Returned.DEFAULT,
      Attribute.Uniqueness.NONE);

  public static final Attribute META = new ComplexAttr("meta",
      Set.copyOf(MetaAttributes.all()),
      false,
      "Service Provider Generated Metadata.",
      false);

  public static List<Attribute> all() {
    return List.of(ID, EXTERNAL_ID, META);
  }
}
