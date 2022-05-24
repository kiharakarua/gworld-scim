package com.iamgusto.users.api.utils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SupportedLocales {

  @Inject
  @ConfigProperty(name = "http.supportedLocales")
  String supportedLocales;
  private Set<Locale> locales = new HashSet<>();

  @PostConstruct
  public void setup() {
    for (String s : supportedLocales.split(",")) {
      locales.add(Locale.forLanguageTag(s));
    }
  }

  public Set<Locale> getLocales() {
    return locales;
  }
}
