package com.iamgusto.users.api;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import java.util.List;
import java.util.Locale;

@RequestScoped
public class RequestLocale {

    @HeaderParam("Accept-Language")
    @DefaultValue(value = "C")
    String languages;

    @Inject
    SupportedLocales supportedLocales;

    @Produces
    @Named("request.locale")
    @RequestScoped
    public Locale getLocale() {
        List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(languages);
        return Locale.lookup(languageRanges, supportedLocales.getLocales());
    }
}
