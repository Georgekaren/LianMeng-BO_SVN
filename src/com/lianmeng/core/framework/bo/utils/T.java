package com.lianmeng.core.framework.bo.utils;

import java.util.logging.Level;

import com.lianmeng.core.framework.bo.utils.logging.InternalLogger;

public class T extends LoggingParameter
  implements WithThrowable
{
  private final Throwable t;

  public T(InternalLogger logger, Level level, Throwable t)
  {
    super(level, logger);
    this.t = t;
  }

  public Throwable getThrowable() {
    return this.t;
  }

  public String toStringInternal() {
    return MessageUtil.build(this, null, this.t);
  }
}
