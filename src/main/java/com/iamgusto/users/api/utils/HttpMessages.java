package com.iamgusto.users.api.utils;

import com.iamgusto.users.utils.Messages;
import java.util.Locale;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.logging.Logger;

@Dependent
public class HttpMessages extends Messages {

  @Inject
  public HttpMessages(Logger logger, @Named("request.locale") Locale requestLocale) {
    super(logger, requestLocale, "http");
  }
}
