package com.lianmeng.core.framework.bo.utils;

import java.util.Collection;

public final class ValidateUtil
{
  public static final boolean validateNotNull(String str)
  {
    return (str != null);
  }

  public static final boolean validateNotEmpty(String str) {
    if (validateNotNull(str)) {
      return (!(str.trim().equals("")));
    }
    return false;
  }

  public static final boolean validateNotNull(Long l) {
    return (l != null);
  }

  public static final boolean validateNotNull(Object o) {
    return (o != null);
  }

  public static final boolean validateNotEmpty(Object[] obj) {
    if (validateNotNull(obj)) {
      return (obj.length != 0);
    }
    return false;
  }

  public static final boolean validateNotEmpty(Collection col) {
    if (validateNotNull(col)) {
      return (col.size() != 0);
    }
    return false;
  }

  public static void isNull(Object object, String message)
  {
    if (!(validateNotNull(object)))
      throw new IllegalArgumentException(message);
  }

  public static void isNull(Object object)
  {
    isNull(object, "[Assertion failed] - the object argument must be null");
  }

  public static void notNull(Object object, String message)
  {
    if (object == null)
      throw new IllegalArgumentException(message);
  }

  public static void notNull(Object object)
  {
    notNull(object, "[Assertion failed] - this argument is required; it cannot be null");
  }

  public static void hasText(String text, String message)
  {
    if (!(validateNotEmpty(text)))
      throw new IllegalArgumentException(message);
  }

  public static void hasText(String text)
  {
    hasText(text, "[Assertion failed] - this String argument must have text; it cannot be <code>null</code>, empty, or blank");
  }

  public static void notEmpty(Object[] array, String message)
  {
    if ((array == null) || (array.length == 0))
      throw new IllegalArgumentException(message);
  }

  public static void notEmpty(String str, String message)
  {
    if (!(validateNotEmpty(str)))
      throw new IllegalArgumentException(message);
  }

  public static void notEmpty(Object[] array)
  {
    notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
  }

  public static void notEmpty(Collection collection, String message)
  {
    if ((collection == null) || (collection.isEmpty()))
      throw new IllegalArgumentException(message);
  }

  public static void notEmpty(Collection collection)
  {
    notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
  }

  public static void state(boolean expression, String message)
  {
    if (!(expression))
      throw new IllegalStateException(message);
  }

  public static void state(boolean expression)
  {
    state(expression, "[Assertion failed] - this state invariant must be true");
  }

  public static void isFalse(boolean expression, String message)
  {
    if (expression)
      throw new IllegalArgumentException(message);
  }

  public static void isTrue(boolean expression, String message)
  {
    if (!(expression))
      throw new IllegalArgumentException(message);
  }
}