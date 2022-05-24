package com.iamgusto.users.data;

import java.io.IOException;

public class WriteException extends Throwable {

  public WriteException(IOException e) {
    super(e);
  }
}
