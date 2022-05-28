package com.iamgusto.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "attributes")
public class Attribute {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long Id;
  @JsonProperty("name")
  @Column(name = "attribute_name")
  private String name;
  @JsonProperty("type")
  @Column(name = "type")
  private Type type;
  @JsonProperty("subAttributes")
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "attribute_sub_attributes",
      joinColumns = @JoinColumn(name = "attribute_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "attribute_sub_attr_id", referencedColumnName = "id"))
  private Set<Attribute> subAttributes;
  @JsonProperty("multiValued")
  @Column(name = "multi_valued")
  private boolean multiValued;
  @JsonProperty("description")
  @Column(name = "description", columnDefinition = "text")
  private String description;
  @JsonProperty("required")
  @Column(name = "required")
  private boolean required;
  @JsonProperty("canonicalValues")
  @ElementCollection
  private List<String> canonicalValues;

  @JsonProperty("caseExact")
  @Column(name = "case_exact")
  private boolean caseExact;

  @JsonProperty("mutability")
  @Column(name = "mutability")
  private Mutability mutability;

  @JsonProperty("returned")
  @Column(name = "returned")
  private Returned returned;

  @JsonProperty("uniqueness")
  @Column(name = "uniqueness")
  private Uniqueness uniqueness;
  @JsonProperty("referenceTypes")
  @ElementCollection
  private List<String> referenceTypes;

  public Attribute() {
  }

  @JsonCreator
  public Attribute(String name, Type type, Set<Attribute> subAttributes, boolean multiValued,
      String description, boolean required, List<String> canonicalValues, boolean caseExact,
      Mutability mutability, Returned returned, Uniqueness uniqueness,
      List<String> referenceTypes) {
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

  public Long getId() {
    return Id;
  }

  public void setId(Long id) {
    Id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Set<Attribute> getSubAttributes() {
    return subAttributes;
  }

  public void setSubAttributes(Set<Attribute> subAttributes) {
    this.subAttributes = subAttributes;
  }

  public boolean isMultiValued() {
    return multiValued;
  }

  public void setMultiValued(boolean multiValued) {
    this.multiValued = multiValued;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public List<String> getCanonicalValues() {
    return canonicalValues;
  }

  public void setCanonicalValues(List<String> canonicalValues) {
    this.canonicalValues = canonicalValues;
  }

  public boolean isCaseExact() {
    return caseExact;
  }

  public void setCaseExact(boolean caseExact) {
    this.caseExact = caseExact;
  }

  public Mutability getMutability() {
    return mutability;
  }

  public void setMutability(Mutability mutability) {
    this.mutability = mutability;
  }

  public Returned getReturned() {
    return returned;
  }

  public void setReturned(Returned returned) {
    this.returned = returned;
  }

  public Uniqueness getUniqueness() {
    return uniqueness;
  }

  public void setUniqueness(Uniqueness uniqueness) {
    this.uniqueness = uniqueness;
  }

  public List<String> getReferenceTypes() {
    return referenceTypes;
  }

  public void setReferenceTypes(List<String> referenceTypes) {
    this.referenceTypes = referenceTypes;
  }

  public static enum Type {
    /**
     * A sequence of zero or more Unicode characters encoded using UTF-8 as per [RFC2277] and
     * [RFC3629]. The JSON format is defined in Section 7 of [RFC7159].
     */
    STRING,
    /**
     * The literal "true" or "false". The JSON format is defined in Section 3 of [RFC7159].
     */
    BOOLEAN,
    /**
     * A real number with at least one digit to the left and right of the period.  The JSON format
     * is defined in Section 6 of [RFC7159].  A decimal has no case sensitivity.
     */
    DECIMAL,
    /**
     * A whole number with no fractional digits or decimal.  The JSON format is defined in Section 6
     * of [RFC7159], with the additional constraint that the value MUST NOT contain fractional or
     * exponent parts.  An integer has no case sensitivity.
     */
    INTEGER,
    /**
     * A DateTime value (e.g., 2008-01-23T04:56:22Z).  The attribute value MUST be encoded as a
     * valid xsd:dateTime as specified in Section 3.3.7 of [XML-Schema] and MUST include both a date
     * and a time.  A date time format has no case sensitivity or uniqueness.
     * <p>
     * Values represented in JSON format MUST conform to the XML constraints above and are
     * represented as a JSON string per Section 7 of [RFC7159].
     */
    dateTime,
    /**
     * A URI for a resource.  A resource MAY be a SCIM resource, an external link to a resource
     * (e.g., a photo), or an identifier such as a URN. The value MUST be the absolute or relative
     * URI of the target resource.
     */
    REFERENCE,
    /**
     * A singular or multi-valued attribute whose value is a composition of one or more simple
     * attributes. The order of the component attributes is not significant.
     */
    COMPLEX,
    BINARY
  }

  public static enum Mutability {
    /**
     * The attribute SHALL NOT be modified.
     */
    readOnly,
    /**
     * The attribute MAY be updated and read at any time. This is the default value.
     */
    readWrite,
    /**
     * The attribute MAY be defined at resource creation (e.g., POST) or at record replacement via a
     * request (e.g., a PUT).  The attribute SHALL NOT be updated.
     */
    IMMUTABLE,
    /**
     * The attribute MAY be updated at any time.  Attribute values SHALL NOT be returned (e.g.,
     * because the value is a stored hash).  Note: An attribute with a mutability of "writeOnly"
     * usually also has a returned setting of "never".
     */
    writeOnly
  }

  public static enum Returned {
    /**
     * The attribute is always returned, regardless of the contents of the "attributes" parameter.
     * For example, "id" is always returned to identify a SCIM resource.
     */
    ALWAYS,
    /**
     * The attribute is never returned.  This may occur because the original attribute value (e.g.,
     * a hashed value) is not retained by the service provider.  A service provider MAY allow
     * attributes to be used in a search filter.
     */
    NEVER,
    /**
     * The attribute is returned by default in all SCIM operation responses where attribute values
     * are returned.  If the GET request "attributes" parameter is specified, attribute values are
     * only returned if the attribute is named in the "attributes" parameter.  DEFAULT.
     */
    DEFAULT,
    /**
     * The attribute is returned in response to any PUT, POST, or PATCH operations if the attribute
     * was specified by the client (for example, the attribute was modified).  The attribute is
     * returned in a SCIM query operation only if specified in the "attributes" parameter.
     */
    REQUEST
  }

  public static enum Uniqueness {
    /**
     * The values are not intended to be unique in any way. DEFAULT.
     */
    NONE,
    /**
     * The value SHOULD be unique within the context of the current SCIM endpoint (or tenancy) and
     * MAY be globally unique (e.g., a "username", email address, or other server-generated key or
     * counter).  No two resources on the same server SHOULD possess the same value.
     */
    SERVER,
    /**
     * The value SHOULD be globally unique (e.g., an email address, a GUID, or other value).  No two
     * resources on any server SHOULD possess the same value.
     */
    GLOBAL
  }
}
