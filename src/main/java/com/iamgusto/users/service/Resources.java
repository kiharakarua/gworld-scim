package com.iamgusto.users.service;

import com.iamgusto.users.api.ResourceTypeApi.ExistsException;
import com.iamgusto.users.model.BaseScimResource;
import com.iamgusto.users.data.ResourceType;
import java.util.List;
import java.util.Optional;

public class Resources {

  public Optional<BaseScimResource> fetch(String id) {
    return null;
  }

  public List<BaseScimResource> fetch() {
    return null;
  }

  public ResourceType create(ResourceType resourceType) throws ExistsException {
    return null;
  }
}
