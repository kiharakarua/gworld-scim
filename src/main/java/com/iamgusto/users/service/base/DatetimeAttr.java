package com.iamgusto.users.service.base;

import java.util.Collections;

public class DatetimeAttr extends SimpleAttr {

    public DatetimeAttr(String name, boolean multiValued, String description, boolean required, Mutability mutability, Returned returned) {
        super(name, Type.DATE_TIME, multiValued, description, false, Collections.emptyList(), required, mutability, returned, Uniqueness.NONE);
    }
}
