package com.iamgusto.users.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.iamgusto.users.model.base.ScimMessage;
import java.util.List;
import java.util.Set;

public class PatchOp implements ScimMessage {

  private final List<Operation> operations;

  public PatchOp(List<Operation> operations) {
    this.operations = operations;
  }

  @Override
  public Set<String> getSchemas() {
    return Set.of("urn:ietf:params:scim:api:messages:2.0:PatchOp");
  }

  private static class Operation {

    private final Op op;
    private final String path;
    private final JsonNode value;

    public Operation(Op op, String path, JsonNode value) {
      this.op = op;
      this.path = path;
      this.value = value;
    }

    public Op getOp() {
      return op;
    }

    public String getPath() {
      return path;
    }

    public JsonNode getValue() {
      return value;
    }
  }

  private static enum Op {
    add, remove, replace;
  }
}
