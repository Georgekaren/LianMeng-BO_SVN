package com.lianmeng.core.framework.bo.utils;

import java.util.logging.Level;

import com.lianmeng.core.framework.bo.server.DynamicDict;
import com.lianmeng.core.framework.bo.utils.logging.InternalLogger;

public class BO extends LoggingParameter
{
  private final DynamicDict bo;

  public BO(InternalLogger logger, Level level, DynamicDict bo)
  {
    super(level, logger);
    this.bo = bo;
  }

  public String toStringInternal() {
    return MessageUtil.build(this, this.bo);
  }
}