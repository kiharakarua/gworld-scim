package com.iamgusto.users.service.base;

import java.util.List;
import java.util.Set;

public class Attribute {
    private final String name;
    private final Type type;
    private final Set<Attribute> subAttributes;
    private final boolean multiValued;
    private final String description;
    private final boolean required;

    private final List<String> canonicalValues;

    private final boolean caseExact;

    private final Mutability mutability;

    private final Returned returned;

    private final Uniqueness uniqueness;

    private final List<String> referenceTypes;

    public Attribute(String name, Type type, Set<Attribute> subAttributes, boolean multiValued, String description, boolean required, List<String> canonicalValues, boolean caseExact, Mutability mutability, Returned returned, Uniqueness uniqueness, List<String> referenceTypes) {
        this.name = name;
        this.type = type;
        this.subAttributes = subAttributes;
        this.multiValued = multiValued;
        this.description = description;
        this.required = required;
        this.canonicalValues = canonicalValues;
        this.caseExact = caseExact;
        this.mutability = mutability;
        this.returned = returned;
        this.uniqueness = uniqueness;
        this.referenceTypes = referenceTypes;
    }

    public static enum Type {
        /**
         * A sequence of zero or more Unicode characters encoded using UTF-8 as
         * per [RFC2277] and [RFC3629]. The JSON format is defined in Section 7
         * of [RFC7159].
         */
        STRING,
        /**
         * The literal "true" or "false". The JSON format is defined in
         * Section 3 of [RFC7159].
         */
        BOOLEAN,
        /**
         * A real number with at least one digit to the left and right of the
         * period.  The JSON format is defined in Section 6 of [RFC7159].  A
         * decimal has no case sensitivity.
         */
        DECIMAL,
        /**
         * A whole number with no fractional digits or decimal.  The JSON format
         * is defined in Section 6 of [RFC7159], with the additional constraint
         * that the value MUST NOT contain fractional or exponent parts.  An
         * integer has no case sensitivity.
         */
        INTEGER,
        /**
         * A DateTime value (e.g., 2008-01-23T04:56:22Z).  The attribute value
         * MUST be encoded as a valid xsd:dateTime as specified in Section 3.3.7
         * of [XML-Schema] and MUST include both a date and a time.  A date time
         * format has no case sensitivity or uniqueness.
         * <p>
         * Values represented in JSON format MUST conform to the XML constraints
         * above and are represented as a JSON string per Section 7 of
         * [RFC7159].
         */
        DATE_TIME,
        /**
         * A URI for a resource.  A resource MAY be a SCIM resource, an external
         * link to a resource (e.g., a photo), or an identifier such as a URN.
         * The value MUST be the absolute or relative URI of the target
         * resource.
         */
        REFERENCE,
        /**
         * A singular or multi-valued attribute whose value is a composition of
         * one or more simple attributes. The order of the component attributes is not
         * significant.
         */
        COMPLEX
    }

    public static enum Mutability {
        /**
         * The attribute SHALL NOT be modified.
         */
        READ_ONLY,
        /**
         * The attribute MAY be updated and read at any time.
         * This is the default value.
         */
        READ_WRITE,
        /**
         * The attribute MAY be defined at resource creation
         * (e.g., POST) or at record replacement via a request (e.g., a
         * PUT).  The attribute SHALL NOT be updated.
         */
        IMMUTABLE,
        /**
         * The attribute MAY be updated at any time.  Attribute
         * values SHALL NOT be returned (e.g., because the value is a
         * stored hash).  Note: An attribute with a mutability of
         * "writeOnly" usually also has a returned setting of "never".
         */
        WRITE_ONLY
    }

    public static enum Returned {
        /**
         * The attribute is always returned, regardless of the
         * contents of the "attributes" parameter.  For example, "id"
         * is always returned to identify a SCIM resource.
         */
        ALWAYS,
        /**
         * The attribute is never returned.  This may occur because
         * the original attribute value (e.g., a hashed value) is not
         * retained by the service provider.  A service provider MAY
         * allow attributes to be used in a search filter.
         */
        NEVER,
        /**
         * The attribute is returned by default in all SCIM
         * operation responses where attribute values are returned.  If
         * the GET request "attributes" parameter is specified,
         * attribute values are only returned if the attribute is named
         * in the "attributes" parameter.  DEFAULT.
         */
        DEFAULT,
        /**
         * The attribute is returned in response to any PUT,
         * POST, or PATCH operations if the attribute was specified by
         * the client (for example, the attribute was modified).  The
         * attribute is returned in a SCIM query operation only if
         * specified in the "attributes" parameter.
         */
        REQUEST
    }

    public static enum Uniqueness {
        /**
         * The values are not intended to be unique in any way.
         * DEFAULT.
         */
        NONE,
        /**
         * The value SHOULD be unique within the context of the
         * current SCIM endpoint (or tenancy) and MAY be globally
         * unique (e.g., a "username", email address, or other
         * server-generated key or counter).  No two resources on the
         * same server SHOULD possess the same value.
         */
        SERVER,
        /**
         * The value SHOULD be globally unique (e.g., an email
         * address, a GUID, or other value).  No two resources on any
         * server SHOULD possess the same value.
         */
        GLOBAL
    }
}
