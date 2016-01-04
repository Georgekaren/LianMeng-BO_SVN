package com.lianmeng.core.framework.bo.utils.logging;

public abstract class InternalLoggerFactory
{
  

  public static void setDefaultFactory(InternalLoggerFactory defaultFactory) {
    if (defaultFactory == null) {
      throw new NullPointerException("defaultFactory");
    }
    defaultFactory = defaultFactory;
  }

 

 

  public abstract InternalLogger newInstance(String paramString);

  public abstract InternalLogger newInstance(String paramString1, String paramString2);
}