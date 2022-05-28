package com.iamgusto.users.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils {

  public static LocalDateTime now() {
    return LocalDateTime.now(ZoneOffset.UTC);
  }
}
