package com.lianmeng.core.framework.bo.utils.logging;

public abstract interface InternalLogger
{
  public abstract String getName();

  public abstract boolean isDebugEnabled();

  public abstract boolean isInfoEnabled();

  public abstract boolean isWarnEnabled();

  public abstract boolean isErrorEnabled();



  public abstract void debug(String paramString);

  public abstract void debug(String paramString, Throwable paramThrowable);

  public abstract void info(String paramString);

  public abstract void info(String paramString, Throwable paramThrowable);

  public abstract void warn(String paramString);

  public abstract void warn(String paramString, Throwable paramThrowable);

  public abstract void error(String paramString);

  public abstract void error(String paramString, Throwable paramThrowable);

 }

