package com.iamgusto.users.service;

import com.iamgusto.users.model.Attribute;
import com.iamgusto.users.model.Schema;

import java.util.ArrayList;
import java.util.List;

public class Schemas {

    private static final List<Attribute> userAttributes = new ArrayList<>();
    public static final Schema USER = new Schema("urn:ietf:params:scim:schemas:core:2.0:User",
            "User",
            "The user schema as defined by scim2",
            userAttributes);

    static {
        userAttributes.addAll(CommonAttributes.all());
    }
}
