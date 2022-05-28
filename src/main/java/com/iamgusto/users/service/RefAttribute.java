package com.iamgusto.users.service;

import com.iamgusto.users.model.Attribute;
import java.util.Collections;
import java.util.List;

public class RefAttribute extends Attribute {

  public RefAttribute(String name, boolean multiValued, String description, boolean required,
      List<String> canonicalValues, Mutability mutability, Returned returned, Uniqueness uniqueness,
      List<String> referenceTypes) {
    super(name, Type.REFERENCE, Collections.emptySet(), multiValued, description, required,
        canonicalValues, true, mutability, returned, uniqueness, referenceTypes);
  }
}