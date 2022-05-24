package com.iamgusto.users.service;

import com.iamgusto.users.model.base.Attribute;

import java.util.ArrayList;
import java.util.List;

public class UserEnterpriseAttributes {

    public static List<Attribute> all() {
        ArrayList<Attribute> objects = new ArrayList<>(UserAttributes.all());
        return objects;
    }
}
