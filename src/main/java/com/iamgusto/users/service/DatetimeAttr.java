package com.iamgusto.users.service;

import com.iamgusto.users.model.Attribute;

import java.util.Collections;

public class DatetimeAttr extends SimpleAttr {

    public DatetimeAttr(String name, boolean multiValued, String description, boolean required, Attribute.Mutability mutability, Attribute.Returned returned) {
        super(name, Attribute.Type.dateTime, multiValued, description, false, Collections.emptyList(), required, mutability, returned, Attribute.Uniqueness.NONE);
    }
}
