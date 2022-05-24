package com.iamgusto.users.api;

import com.iamgusto.users.utils.Messages;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

@RequestScoped
public class HttpMessages extends Messages {

    @Inject
    public HttpMessages(Logger logger, @Named("request.locale") Locale requestLocale) {
        super(logger, requestLocale, "http");
    }
}
