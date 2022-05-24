package com.iamgusto.users.model;

import java.util.Collections;
import java.util.Set;

public class Error implements Resource {
    private final int status;
    private final String detail;
    private final String scimType;

    public Error(int status, String detail, String scimType) {
        this.status = status;
        this.detail = detail;
        this.scimType = scimType;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public String getScimType() {
        return scimType;
    }

    public Set<String> getSchemas() {
        return Collections.singleton("urn:ietf:params:scim:api:messages:2.0:Error");
    }
}
