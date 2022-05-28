package com.iamgusto.users.api;

import com.iamgusto.users.api.utils.Parser;
import com.iamgusto.users.data.ResourceType.SchemaExtension;
import com.iamgusto.users.data.Schema;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class Filters {

  private final Schema schema;
  private final Collection<SchemaExtension> schemaExtensions;
  private final Expression expression;

  public Filters(Schema schema, Collection<SchemaExtension> schemaExtensions,
      Expression expressions) {
    this.schema = schema;
    this.schemaExtensions = schemaExtensions;
    this.expression = expressions;
  }

  public static Filters parse(String filter, Schema schema,
      Collection<SchemaExtension> schemaExtensions) throws ParseException {
    return new Filters(schema, schemaExtensions, Parser.tokenize(filter));
  }

  public String toHql() {
    return "";
  }

  public enum CompareOp {
    EQUAL_TO("eq"),
    LESS_THAN("lt"),
    LESS_THAN_EQUAL_TO("le"),
    GREATER_THAN("gt"),
    GREATER_THAN_EQUAL_TO("ge"),
    CONTAINS("co"),
    STARTS_WITH("sw"),
    ENDS_WITH("ew");

    private final String exp;

    CompareOp(String exp) {
      this.exp = exp;
    }

    public static CompareOp fromString(String v) {
      for (CompareOp value : values()) {
        if (value.exp.equalsIgnoreCase(v)) {
          return value;
        }
      }
      return valueOf(CompareOp.class, v);
    }

    public CompareExpression compare(String path, String value) {
      return new CompareExpression(new AttrPath(path),
          this,
          new CompValue("\"" + value + "\""));
    }

    public CompareExpression compare(String path, boolean value) {
      return new CompareExpression(new AttrPath(path),
          this,
          new CompValue(value));
    }

    public CompareExpression isNull(String path) {
      return new CompareExpression(new AttrPath(path),
          this,
          new CompValue("null"));
    }

    public String getExp() {
      return exp;
    }

    @Override
    public String toString() {
      return exp;
    }
  }

  public enum LogicalComp {
    AND, OR;
  }

  public enum SingleOp {
    PRESENT("pr"), NOT("not");

    private final String exp;

    SingleOp(String exp) {
      this.exp = exp;
    }

    public static SingleOp fromString(String v) {
      for (SingleOp value : values()) {
        if (value.exp.equalsIgnoreCase(v)) {
          return value;
        }
      }
      return valueOf(SingleOp.class, v);
    }

    public String getExp() {
      return exp;
    }
  }

  public static interface Expression {

    Expression not();
  }

  public static interface SimpleExpression extends Expression {

    @Override
    SimpleExpression not();
  }

  public record NestedExpression(AttrPath parentPath, SimpleExpression child) implements
      Expression {

    public NestedExpression not() {
      return new NestedExpression(parentPath(), child().not());
    }
  }

  public record AttrPath(String path) {

    public static AttrPath of(String path) {
      return new AttrPath(path);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      AttrPath attrPath = (AttrPath) o;
      return Objects.equals(path, attrPath.path);
    }

    @Override
    public int hashCode() {
      return Objects.hash(path);
    }
  }

  public abstract static class AttrExpression implements SimpleExpression {

    private final AttrPath path;
    private final boolean not;

    protected AttrExpression(AttrPath path, boolean not) {
      this.path = path;
      this.not = not;
    }

    public AttrPath getPath() {
      return path;
    }

    public boolean isNot() {
      return not;
    }

    @Override
    public abstract AttrExpression not();

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      AttrExpression that = (AttrExpression) o;
      return not == that.not && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
      return Objects.hash(path, not);
    }
  }

  public static final class CompareExpression extends AttrExpression {

    private final CompareOp compareOp;
    private final CompValue compValue;

    public CompareExpression(AttrPath path, CompareOp compareOp,
        CompValue compValue) {
      this(path, compareOp, compValue, false);
    }

    private CompareExpression(AttrPath path, CompareOp compareOp,
        CompValue compValue, boolean not) {
      super(path, not);
      this.compareOp = compareOp;
      this.compValue = compValue;
    }

    public CompareOp getCompareOp() {
      return compareOp;
    }

    public CompValue getCompValue() {
      return compValue;
    }

    @Override
    public CompareExpression not() {
      return new CompareExpression(getPath(), getCompareOp(), getCompValue(), !isNot());
    }

    @Override
    public String toString() {
      return "CompareExpression{" +
          "compareOp=" + compareOp +
          ", compValue=" + compValue +
          ", path=" + getPath() +
          ", not=" + isNot() +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      if (!super.equals(o)) {
        return false;
      }
      CompareExpression that = (CompareExpression) o;
      return compareOp == that.compareOp && Objects.equals(compValue, that.compValue);
    }

    @Override
    public int hashCode() {
      return Objects.hash(super.hashCode(), compareOp, compValue);
    }
  }

  public static class PresentExpression extends AttrExpression {

    public PresentExpression(AttrPath path) {
      this(path, false);
    }

    public PresentExpression(AttrPath path, boolean not) {
      super(path, not);
    }



    public static Expression of(String path) {
      return new PresentExpression(new AttrPath(path));
    }

    @Override
    public PresentExpression not() {
      return new PresentExpression(getPath(), !isNot());
    }


  }

  public record LogicalExpression(LogicalComp logicalComp, Expression exp1,
                                  Expression exp2, boolean isNot) implements SimpleExpression {

    public LogicalExpression(LogicalComp logicalComp, Expression exp1, Expression exp2) {
      this(logicalComp, exp1, exp2, false);
    }

    public static LogicalExpression and(Expression e1, Expression e2) {
      return new LogicalExpression(LogicalComp.AND, e1, e2);
    }

    public static Expression or(Expression e1, Expression e2) {
      return new LogicalExpression(LogicalComp.OR, e1, e2);
    }

    @Override
    public LogicalExpression not() {
      return new LogicalExpression(logicalComp(), exp1(), exp2(), !isNot());
    }
  }

  public record CompValue(Object value) {

    public static CompValue of(String s) {
      return new CompValue(s);
    }
  }
}
