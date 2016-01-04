package com.lianmeng.core.framework.bo.utils;

public abstract class NumberUtil
{
  public static final long DIV = 10000L;

  public static float divLongToFloat(long value)
  {
    float ret = (float)value / 10000.0F;
    return ret;
  }

  public static double divLongToDouble(long value) {
    double ret = value / 10000.0D;
    return ret;
  }

  public static String divLongToString(long value)
  {
    String ret = Double.toString(value / 10000.0D);
    return ret;
  }

  public static String fomartLong(long ll, int deciLen)
  {
    String strMoney = Long.toString(ll);
    StringBuffer retSb = new StringBuffer();

    if ("-".equals(strMoney.substring(0, 1)))
    {
      retSb.append("-");

      strMoney = strMoney.substring(1);
    }

    int len = strMoney.length();

    if (len <= deciLen)
    {
      int iRex = deciLen - len;
      retSb.append("0").append(".");
      for (int i = 0; i < iRex; ++i) {
        retSb.append("0");
      }
      retSb.append(strMoney.substring(0));
    }
    else
    {
      int offset = len - deciLen;
      retSb.append(strMoney.substring(0, offset)).append(".").append(strMoney.substring(offset));
    }

    String ret = retSb.toString();
    for (int i = 0; i < 4; ++i) {
      if (!("0".equals(ret.substring(ret.length() - 1)))) {
        break;
      }
      ret = ret.substring(0, ret.length() - 1);
      if (i == 3) {
        ret = ret.substring(0, ret.length() - 1);
      }
    }
    return ret;
  }

  public static long mulFloatToLong(float value)
  {
    return (long) (value * 10000.0F);
  }
}