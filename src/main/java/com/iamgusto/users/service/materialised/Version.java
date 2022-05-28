package com.iamgusto.users.service.materialised;

/**
 * The version of the resource being returned. This value must be the same as the entity-tag (ETag)
 * HTTP response header (see Sections 2.1 and 2.3 of [RFC7232]). This attribute has "caseExact" as
 * "true".  Service provider support for this attribute is optional and subject to the service
 * provider's support for versioning (see Section 3.14 of [RFC7644]). If a service provider provides
 * "version" (entity-tag) for a representation and the generation of that entity-tag does not
 * satisfy all the characteristics of a strong validator (see Section 2.1 of [RFC7232]), then the
 * origin server MUST mark the "version" (entity-tag) as weak by prefixing its opaque value with
 * "W/" (case sensitive).
 */
public interface Version {

}
