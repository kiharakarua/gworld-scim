package com.iamgusto.users.api;

import com.iamgusto.users.data.Schema;

public class Sorting {

  public static Sorting parse(String sort, Schema schema) {
    return new Sorting();
  }

  public String toHql() {
    return "";
  }
}
