package com.iamgusto.users.service;

import com.iamgusto.users.model.base.Attribute;

import java.util.Collections;
import java.util.List;

public class MetaAttributes {
    public static final Attribute RESOURCE_TYPE = new SimpleAttr("resourceType",
            Attribute.Type.STRING,
            false,
            "The name of the resource type of the resource.",
            true,
            Collections.emptyList(),
            true,
            Attribute.Mutability.readOnly,
            Attribute.Returned.DEFAULT,
            Attribute.Uniqueness.NONE);

    public static final Attribute CREATED = new DatetimeAttr("created",
            false,
            "The \"DateTime\" that the resource was added to the service provider.",
            true,
            Attribute.Mutability.readOnly,
            Attribute.Returned.DEFAULT);
    public static final Attribute LAST_MODIFIED = new DatetimeAttr("lastModified",
            false,
            "The \"DateTime\" that the resource was added to the service provider.",
            true,
            Attribute.Mutability.readOnly,
            Attribute.Returned.DEFAULT);

    public static final Attribute LOCATION = new SimpleAttr("location",
            Attribute.Type.STRING,
            false,
            "The URI of the resource being returned. This value MUST  be the same as the \"Content-Location\" HTTP response header",
            true,
            Collections.emptyList(),
            false,
            Attribute.Mutability.readOnly,
            Attribute.Returned.DEFAULT,
            Attribute.Uniqueness.NONE);


    public static final Attribute VERSION = new SimpleAttr("version",
            Attribute.Type.STRING,
            false,
            "The version of the resource being returned.  This value  must be the same as the entity-tag (ETag) HTTP response header.",
            false,
            Collections.emptyList(),
            true,
            Attribute.Mutability.readOnly,
            Attribute.Returned.DEFAULT,
            Attribute.Uniqueness.NONE);

    public static List<Attribute> all() {
        return List.of(RESOURCE_TYPE, CREATED, LAST_MODIFIED, LOCATION, VERSION);
    }
}
