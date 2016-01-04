package com.lianmeng.core.framework.bo.utils;

import java.util.logging.Level;

import com.lianmeng.core.framework.bo.utils.logging.InternalLogger;

public class Msg extends LoggingParameter
{
  private final String msg;

  public Msg(InternalLogger logger, Level level, String msg)
  {
    super(level, logger);
    this.msg = msg;
  }

  public String toStringInternal() {
    return MessageUtil.build(this, this.msg);
  }
}