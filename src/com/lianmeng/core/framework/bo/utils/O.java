package com.lianmeng.core.framework.bo.utils;

import java.util.logging.Level;

import com.lianmeng.core.framework.bo.utils.logging.InternalLogger;

public class O extends LoggingParameter
{
  private final Object o;

  public O(InternalLogger logger, Level level, Object o)
  {
    super(level, logger);
    this.o = o;
  }

  public String toStringInternal() {
    String msg = (this.o == null) ? null : this.o.toString();
    return MessageUtil.build(this, msg);
  }
}