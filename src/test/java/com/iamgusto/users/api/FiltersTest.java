package com.iamgusto.users.api;

import com.iamgusto.users.api.Filters.CompareOp;
import com.iamgusto.users.api.Filters.Expression;
import com.iamgusto.users.api.Filters.LogicalExpression;
import com.iamgusto.users.api.Filters.PresentExpression;
import com.iamgusto.users.api.utils.Parser;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class FiltersTest {


  @Test
  public void testParserInputOutput() {

    String[] input = new String[]{
        "userName eq \"bjensen\"",
        "name.familyName co \"O'Malley\"",
        "userName sw \"J\"",
        "urn:ietf:params:scim:schemas:core:2.0:User:userName sw \"J\"",
        "title pr",
        "meta.lastModified gt \"2011-05-13T04:42:34Z\"",
        "meta.lastModified ge \"2011-05-13T04:42:34Z\"",
        "meta.lastModified lt \"2011-05-13T04:42:34Z\"",
        "meta.lastModified le \"2011-05-13T04:42:34Z\"",
        "title pr and userType eq \"Employee\"",
        "title pr or userType eq \"Intern\"",
        "\n schemas eq \"urn:ietf:params:scim:schemas:extension:enterprise:2.0:User\"",
        "userType eq \"Employee\" and (emails co \"example.com\" or\n emails.value co \"example.org\")",
        "userType ne \"Employee\" and not (emails co \"example.com\" or emails.value co \"example.org\")",
        "userType eq \"Employee\" and (emails.type eq \"work\")",
        "userType eq \"Employee\" and emails[type eq \"work\" and value co \"@example.com\"]",
        "emails[type eq \"work\" and value co \"@example.com\"] or ims[type eq \"xmpp\" and value co \"@foo.com\"]"};
    for (String in : input) {
      Assertions.assertDoesNotThrow(() -> Parser.tokenize(in));
    }
  }

  @ParameterizedTest
  @ArgumentsSource(value = ParseableFIltersArgProvider.class)
  public void testInput(String expression, Expression parsed) {
    try {
      Expression tokenize = Parser.tokenize(expression);
      Assertions.assertEquals(parsed, tokenize);
    } catch (ParseException e) {
      Assertions.fail("Failed: " + expression, e);
    }
  }

  public record Arg(String string, Expression expression) {

  }

  private static class ParseableFIltersArgProvider implements ArgumentsProvider {

    static Arg[] args = new Arg[]{
        new Arg("userName eq \"bjensen\"",
            CompareOp.EQUAL_TO.compare("userName", "bjensen")),
        new Arg("name.familyName co \"O'Malley\"",
            CompareOp.CONTAINS.compare("name.familyName", "O'Malley")),
        new Arg("userName sw \"J\"",
            CompareOp.STARTS_WITH.compare("userName", "J")),
        new Arg("urn:ietf:params:scim:schemas:core:2.0:User:userName sw \"J\"",
            CompareOp.STARTS_WITH.compare("urn:ietf:params:scim:schemas:core:2.0:User:userName",
                "J")),
        new Arg("title pr", PresentExpression.of("title")),
        new Arg("meta.lastModified gt \"2011-05-13T04:42:34Z\"",
            CompareOp.GREATER_THAN.compare("meta.lastModified", "2011-05-13T04:42:34Z")),
        new Arg("meta.lastModified ge \"2011-05-13T04:42:34Z\"",
            CompareOp.GREATER_THAN_EQUAL_TO.compare("meta.lastModified", "2011-05-13T04:42:34Z")),
        new Arg("meta.lastModified lt \"2011-05-13T04:42:34Z\"",
            CompareOp.LESS_THAN.compare("meta.lastModified", "2011-05-13T04:42:34Z")),
        new Arg("meta.lastModified le \"2011-05-13T04:42:34Z\"",
            CompareOp.LESS_THAN_EQUAL_TO.compare("meta.lastModified", "2011-05-13T04:42:34Z")),

        new Arg("title pr and userType eq \"Employee\"",
            LogicalExpression.and(PresentExpression.of("title"),
                CompareOp.EQUAL_TO.compare("userType", "Employee"))),
        new Arg("title pr or userType eq \"Intern\"",
            LogicalExpression.or(PresentExpression.of("title"),
                CompareOp.EQUAL_TO.compare("userType", "Intern"))),
        new Arg("\n schemas eq \"urn:ietf:params:scim:schemas:extension:enterprise:2.0:User\"",
            CompareOp.EQUAL_TO.compare("schemas",
                "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User")),

        new Arg(
            "userType eq \"Employee\" and (emails co \"example.com\" or emails.value co \"example.org\")",
            LogicalExpression.and(CompareOp.EQUAL_TO.compare("userType", "Employee"),
                LogicalExpression.or(CompareOp.CONTAINS.compare("emails", "example.com"),
                    CompareOp.CONTAINS.compare("emails.value", "example.org")))),
        new Arg(
            "userType ne \"Employee\" and not (emails co \"example.com\" or emails.value co \"example.org\")",
            LogicalExpression.and(CompareOp.EQUAL_TO.compare("userType", "Employee").not(),
                LogicalExpression.or(CompareOp.CONTAINS.compare("emails", "example.com"),
                    CompareOp.CONTAINS.compare("emails.value", "example.org")).not())),
        new Arg("userType eq \"Employee\" and (emails.type eq \"work\")",
            LogicalExpression.and(CompareOp.EQUAL_TO.compare("userType", "Employee"),
                CompareOp.EQUAL_TO.compare("emails.type", "work")))

    };

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Arrays.stream(args).map(arg -> () -> new Object[]{arg.string(), arg.expression()});
    }
  }
}