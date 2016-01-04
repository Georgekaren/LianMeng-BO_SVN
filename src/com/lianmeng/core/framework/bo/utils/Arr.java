package com.lianmeng.core.framework.bo.utils;
import java.util.logging.Level;

import com.lianmeng.core.framework.bo.utils.logging.InternalLogger;

public class Arr extends LoggingParameter
{
  private final Object[] arr;

  public Arr(InternalLogger logger, Level level, Object[] arr)
  {
    super(level, logger);
    this.arr = arr;
  }

  public String toStringInternal()
  {
    String msg;
    if (this.arr == null) {
      msg = "";
    }
    else {
      StringBuilder sb = new StringBuilder();
      for (int index = 0; index < this.arr.length; ++index) {
        sb.append(this.arr[index]);
      }
      msg = sb.toString();
    }
    return MessageUtil.build(this, msg);
  }
}