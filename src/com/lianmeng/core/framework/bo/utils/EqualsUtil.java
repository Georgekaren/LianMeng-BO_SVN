package com.lianmeng.core.framework.bo.utils;

public final class EqualsUtil
{
  public static final int SAME_OBJECT = 1;
  public static final int SAME_CLASS = 2;
  public static final int NOT_SAME_OBJECT_AND_CLASS = 0;
  private static final int TRUE = 1;

  public static boolean equals(int a, int b)
  {
    return (a == b);
  }

  public static boolean equals(long a, long b)
  {
    return (a == b);
  }

  public static boolean equals(float a, float b)
  {
    return (a == b);
  }

  public static boolean equals(short a, short b)
  {
    return (a == b);
  }

  public static boolean equals(double a, double b)
  {
    return (a == b);
  }

  public static boolean equalsTrim(String a, String b)
  {
    if (a == b) {
      return true;
    }
    if ((a == null) || (b == null)) {
      return false;
    }
    return a.trim().equals(b.trim());
  }

  public static boolean equalsIgnoreCase(String a, String b)
  {
    if (a == b) {
      return true;
    }
    if ((a == null) || (b == null)) {
      return false;
    }
    return a.equalsIgnoreCase(b);
  }

  public static boolean equals(Object[] a, Object[] b)
  {
    if (a == b) {
      return true;
    }
    if ((a == null) || (b == null)) {
      return false;
    }
    if (a.length != b.length) {
      return false;
    }
    for (int i = 0; i < a.length; ++i) {
      if (!(equals(a[i], b[i]))) {
        return false;
      }
    }
    return true;
  }

  public static boolean equals(Object a, Object b)
  {
    if (a == b) {
      return true;
    }
    if ((a == null) || (b == null)) {
      return false;
    }
    return a.equals(b);
  }

  public static int preEquals(Object a, Object b)
  {
    if ((a == null) || (b == null)) {
      return 0;
    }
    if (a == b) {
      return 1;
    }
    if (a.getClass() == b.getClass()) {
      return 2;
    }

    return 0;
  }

  private static boolean int2Bool(int val)
  {
    return (val == 1);
  }
}