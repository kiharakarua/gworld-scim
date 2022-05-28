package com.iamgusto.users.api.models;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.iamgusto.users.model.BaseScimResource;
import java.util.Set;

public class BaseResponse extends BaseScimResource {

  private Set<String> schemas;
  private ArrayNode data;

  @Override
  public Set<String> getSchemas() {
    return schemas;
  }
}
