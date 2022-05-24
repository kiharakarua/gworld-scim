package com.iamgusto.users.model.base.serviceprovider;

import com.iamgusto.users.model.base.BaseScimResource;
import java.util.Collections;
import java.util.Set;

public class ServiceProviderConfig extends BaseScimResource {

  public ServiceProviderConfig() {
    super(null);
  }

  @Override
  public Set<String> getSchemas() {
    return Collections.singleton("urn:ietf:params:scim:schemas:core:2.0:ServiceProviderConfig");
  }
}
