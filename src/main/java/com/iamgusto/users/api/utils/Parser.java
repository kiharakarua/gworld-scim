package com.iamgusto.users.api.utils;

import com.iamgusto.users.api.Filters.AttrExpression;
import com.iamgusto.users.api.Filters.AttrPath;
import com.iamgusto.users.api.Filters.CompValue;
import com.iamgusto.users.api.Filters.CompareExpression;
import com.iamgusto.users.api.Filters.CompareOp;
import com.iamgusto.users.api.Filters.Expression;
import com.iamgusto.users.api.Filters.LogicalComp;
import com.iamgusto.users.api.Filters.LogicalExpression;
import com.iamgusto.users.api.Filters.PresentExpression;
import java.text.ParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Parser {

  Pattern BRACKET_OR_NOT = Pattern.compile("^(\\(.*\\)|(?i)not\\p{javaWhitespace})");

  Pattern OPERANDS = Pattern.compile("(?i)\\p{javaWhitespace}(eq|ne|gt|ge|lt|le|pr|co|sw|ew)");

  Pattern LOGICAL_OPERATORS = Pattern.compile(
      "(?i)^(and\\p{javaWhitespace}|or\\p{javaWhitespace})");

  static void main(String[] args) throws ParseException {
    Matcher matcher = LOGICAL_OPERATORS.matcher("or\t");
    System.out.println("matcher.find() = " + matcher.find());
  }

  static Expression tokenize(final String r) throws ParseException {
    return tokenize(0, r);
  }

  private static Expression tokenize(int idx, final String r) throws ParseException {
    final String eval = r.stripLeading();
    idx = idx + r.length() - eval.length();
    Matcher matcher = BRACKET_OR_NOT.matcher(eval);
    if (matcher.find()) {
      if (matcher.group().startsWith("(")) {

        String substring = eval.substring(1, matcher.end() - 1);
        System.out.println("substring = " + substring);
        final Expression e1 = tokenize(idx, substring);

        idx = idx + matcher.end();
        Optional<Pair<LogicalComp, Expression>> dangling = tokenizeDangling(idx,
            eval.substring(matcher.end()));
        if (dangling.isPresent()) {
          Pair<LogicalComp, Expression> pair = dangling.get();
          return new LogicalExpression(pair.k1(), e1, pair.k2());
        } else {
          return e1;
        }
      } else {
        return tokenize(idx + matcher.end(), eval.substring(matcher.end()))
            .not();
      }
    } else {
      return tokenizeOther2Plain(idx, eval);
    }
  }

  private static Expression tokenizeOther2Plain(int idx, String r) throws ParseException {
    String eval = r.stripLeading();
    idx = idx + r.length() - eval.length();
    Matcher matcher = OPERANDS.matcher(eval);
    if (matcher.find()) {
      final AttrPath attr1 = new AttrPath(eval.substring(0, matcher.start()).trim());
      final AttrExpression e1;
      final String eval2;
      String operator = matcher.group().trim();
      if (operator.equalsIgnoreCase("pr")) {
        e1 = new PresentExpression(attr1);
        eval2 = eval.substring(matcher.end());
      } else {
        boolean negate = false;
        if (operator.equalsIgnoreCase("ne")) {
          negate = true;
          operator = "eq";
        }
        final CompareOp op = CompareOp.fromString(operator);
        String x2 = eval.substring(matcher.end()).stripLeading();
        final CompValue attr2;
        int endOfE1OnX2;
        if (x2.startsWith("\"")) {
          endOfE1OnX2 = x2.indexOf("\" ", 1);
          if (endOfE1OnX2 == -1) {
            endOfE1OnX2 = x2.length();
          } else {
            endOfE1OnX2 += 1;
          }
        } else {
          endOfE1OnX2 = x2.indexOf(" ");
          if (endOfE1OnX2 == -1) {
            endOfE1OnX2 = x2.length();
          }
        }
        attr2 = new CompValue(x2.substring(0, endOfE1OnX2).trim());
        eval2 = x2.substring(endOfE1OnX2);
        e1 = negate ? new CompareExpression(attr1, op, attr2).not()
            : new CompareExpression(attr1, op, attr2);
      }
      idx = idx + eval.length() - eval2.length();
      Optional<Pair<LogicalComp, Expression>> logicalDangling = tokenizeDangling(idx, eval2);
      if (logicalDangling.isPresent()) {
        Pair<LogicalComp, Expression> pair = logicalDangling.get();
        return new LogicalExpression(pair.k1(), e1, pair.k2());
      } else {
        return e1;
      }
    } else {
      throw new ParseException("Unable to find valid operand in: =>" + eval, idx);
    }
  }

  private static Optional<Pair<LogicalComp, Expression>> tokenizeDangling(int idx, final String r)
      throws ParseException {
    final String eval = r.stripLeading();
    idx = idx + r.length() - eval.length();
    if (eval.isBlank()) {
      return Optional.empty();
    }
    Matcher matcher = LOGICAL_OPERATORS.matcher(eval);
    if (matcher.find()) {
      idx = idx + matcher.end();
      String substring = eval.substring(matcher.end());
      if (matcher.group().toLowerCase().startsWith("or")) {
        return Optional.of(Pair.of(LogicalComp.OR, tokenize(idx, substring)));
      } else {
        return Optional.of(Pair.of(LogicalComp.AND, tokenize(idx, substring)));
      }
    } else {
      throw new ParseException("Unable to find logical operator for the next value =>" + eval, idx);
    }
  }
}
