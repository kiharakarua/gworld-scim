package com.iamgusto.users.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iamgusto.users.data.ResourceType.SchemaExtension;
import com.iamgusto.users.data.Schema;
import java.util.Collection;

public record NodeWriter(ObjectMapper objectMapper) {

  public ObjectNode write(Object t, Schema schema, Collection<SchemaExtension> extensions) {
    return objectMapper.valueToTree(t);
  }

  public <T> ArrayNode write(Collection<T> items, Schema schema,
      Collection<SchemaExtension> extensions) {
    ArrayNode arrayNode = objectMapper.createArrayNode();
    for (T t : items) {
      arrayNode.add(write(t, schema, extensions));
    }
    return arrayNode;
  }
}
