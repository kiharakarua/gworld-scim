package com.iamgusto.users.api.utils;

import org.apache.tinkerpop.gremlin.process.traversal.P;

public record Pair<K1, K2>(K1 k1, K2 k2) {

  public static <P1, P2> Pair<P1, P2> of(P1 o, P2 o1) {
    return new Pair<>(o, o1);
  }
}
