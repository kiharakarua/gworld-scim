package com.iamgusto.users.service;

import com.iamgusto.users.model.Attribute;

import java.util.Collections;
import java.util.Set;

public class ComplexAttr extends Attribute {

    public ComplexAttr(String name, Set<Attribute> subAttributes, boolean multiValued, String description, boolean required) {
        super(name, Type.COMPLEX, subAttributes, multiValued, description, required, Collections.emptyList(), false, Mutability.readWrite, Returned.DEFAULT, Uniqueness.NONE, Collections.emptyList());
    }
}
