package com.iamgusto.users.service;

import com.iamgusto.users.model.Attribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAttributes {

    private final static Attribute USER_NAME = new SimpleAttr("userName", Attribute.Type.STRING, false, "A service provider's unique identifier for the user, typically used by the user to directly authenticate to the service provider.", true, Collections.emptyList(), false, Attribute.Mutability.IMMUTABLE, Attribute.Returned.DEFAULT, Attribute.Uniqueness.SERVER);
    private final static Attribute DISPLAY_NAME = new SimpleAttr("displayName", Attribute.Type.STRING, false, "The name of the user, suitable for display to end-users.", true, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute NICK_NAME = new SimpleAttr("nickName", Attribute.Type.STRING, false, "The casual way to address the user in real life, e.g., \"Bob\" or \"Bobby\" instead of \"Robert\".", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute PROFILE_URL = new SimpleAttr("profileUrl", Attribute.Type.STRING, false, "A URI that is a uniform resource locator (as defined in\n      Section 1.1.3 of [RFC3986]) and that points to a location\n      representing the user's online profile", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute TITLE = new SimpleAttr("title", Attribute.Type.STRING, false, "The user's title, such as \"Vice President\".", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute USER_TYPE = new SimpleAttr("userType", Attribute.Type.STRING, false, "Used to identify the relationship between the organization and the user.", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute PREFERRED_LANGUAGE = new SimpleAttr("preferredLanguage", Attribute.Type.STRING, false, "Indicates the user's preferred written or spoken languages and is generally used for selecting a localized user interface.", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute LOCALE = new SimpleAttr("locale", Attribute.Type.STRING, false, "Used to indicate the User's default location for purposes of localizing such items as currency, date time format, or numerical representations.", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute TIMEZONE = new SimpleAttr("timezone", Attribute.Type.STRING, false, "The User's time zone, in IANA Time Zone database format [RFC6557]", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute ACTIVE = new SimpleAttr("active", Attribute.Type.BOOLEAN, false, "A Boolean value indicating the user's administrative status. The definitive meaning of this attribute is determined by the service provider.", false, Collections.emptyList(), false, Attribute.Mutability.readWrite, Attribute.Returned.DEFAULT, Attribute.Uniqueness.NONE);

    private static final Attribute PASSWORD = new SimpleAttr("password", Attribute.Type.STRING, false, "This attribute is intended to be used as a means to set, replace, or compare (i.e., filter for equality) a password.",
            false, Collections.emptyList(), true, Attribute.Mutability.writeOnly, Attribute.Returned.NEVER, Attribute.Uniqueness.NONE);

    public static List<Attribute> all() {
        ArrayList<Attribute> objects = new ArrayList<>(CommonAttributes.all());
        objects.add(USER_NAME);
        objects.addAll(NameAttributes.all());
        objects.add(DISPLAY_NAME);
        objects.add(NICK_NAME);
        objects.add(PROFILE_URL);
        objects.add(TITLE);
        objects.add(USER_TYPE);
        objects.add(PREFERRED_LANGUAGE);
        return objects;
    }
}
