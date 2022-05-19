package com.iamgusto.users.service.base;

import java.util.List;

public class Schema {

    //URI
    private final String id;
    private final String name;
    private final String description;
    private final List<Attribute> attributes;

    public Schema(String id, String name, String description, List<Attribute> attributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.attributes = attributes;
    }

}
