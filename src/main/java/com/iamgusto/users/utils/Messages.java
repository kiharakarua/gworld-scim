package com.iamgusto.users.utils;

import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private final Locale requestLocale;
    private final String prefix;
    private Logger logger;

    public Messages(Logger logger, Locale requestLocale, String prefix) {
        this.logger = logger;
        this.requestLocale = requestLocale;
        this.prefix = prefix;
    }

    public String of(String template, Object... fmt) {
        String value = ConfigProvider.getConfig().getConfigValue(template).getValue();
        if (value == null) {
            return template;
        }
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(prefix, requestLocale);
            String pattern = bundle.getString(template);
            return MessageFormat.format(pattern, fmt);
        } catch (Exception e) {
            logger.errorf(e, "Unable to format %s message %s with values %s in %s", prefix, template,
                    Arrays.toString(fmt), requestLocale.toString());
            return template;
        }
    }
}
