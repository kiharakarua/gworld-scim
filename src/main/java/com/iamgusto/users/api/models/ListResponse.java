package com.iamgusto.users.api.models;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.iamgusto.users.model.ScimMessage;
import java.util.Objects;
import java.util.Set;

public class ListResponse implements ScimMessage {

  private final long totalResults;
  private final int startIndex;
  private final int itemsPerPage;
  private final ArrayNode Resources;

  public ListResponse(long totalResults, int startIndex, int itemsPerPage, ArrayNode resources) {
    this.totalResults = totalResults;
    this.startIndex = startIndex;
    this.itemsPerPage = itemsPerPage;
    Resources = resources;
  }

  public long getTotalResults() {
    return totalResults;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getItemsPerPage() {
    return itemsPerPage;
  }

  public ArrayNode getResources() {
    return Resources;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListResponse that = (ListResponse) o;
    return totalResults == that.totalResults && startIndex == that.startIndex
        && itemsPerPage == that.itemsPerPage && Objects.equals(Resources, that.Resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalResults, startIndex, itemsPerPage, Resources);
  }

  @Override
  public Set<String> getSchemas() {
    return Set.of("urn:ietf:params:scim:api:messages:2.0:ListResponse");
  }
}
