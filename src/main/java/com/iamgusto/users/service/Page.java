package com.iamgusto.users.service;

import com.iamgusto.users.model.BaseScimResource;
import java.util.List;
import java.util.Objects;

public class Page<T extends BaseScimResource> {

  private final int itemsPerPage;
  private final long totalResults;
  private final int startIndex;
  private final List<T> items;

  public Page(int itemsPerPage, long totalResults, int startIndex, List<T> items) {
    this.itemsPerPage = itemsPerPage;
    this.totalResults = totalResults;
    this.startIndex = startIndex;
    this.items = items;
  }

  public int getItemsPerPage() {
    return itemsPerPage;
  }

  public long getTotalResults() {
    return totalResults;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public List<T> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Page<?> page = (Page<?>) o;
    return itemsPerPage == page.itemsPerPage && totalResults == page.totalResults
        && startIndex == page.startIndex && Objects.equals(items, page.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemsPerPage, totalResults, startIndex, items);
  }
}
