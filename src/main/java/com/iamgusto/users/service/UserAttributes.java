package com.iamgusto.users.service;

import com.iamgusto.users.service.base.Attribute;
import com.iamgusto.users.service.base.ComplexAttr;
import com.iamgusto.users.service.base.SimpleAttr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class UserAttributes {

    public static List<Attribute> all() {
        ArrayList<Attribute> objects = new ArrayList<>(CommonAttributes.all());
        return objects;
    }
}
