package com.iamgusto.users.service;

import com.iamgusto.users.model.Attribute;

import java.util.Collections;
import java.util.List;

public class SimpleAttr extends Attribute {

    public SimpleAttr(String name, Type type, boolean multiValued, String description, boolean required, List<String> canonicalValues, boolean caseExact, Mutability mutability, Returned returned, Uniqueness uniqueness) {
        super(name, type, Collections.emptySet(), multiValued, description, required, canonicalValues, caseExact, mutability, returned, uniqueness, Collections.emptyList());
    }
}
