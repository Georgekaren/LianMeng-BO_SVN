package com.lianmeng.core.framework.bo.utils;

import java.util.logging.Level;

import com.lianmeng.core.framework.bo.utils.logging.InternalLogger;

public abstract class LoggingParameter
{
  public static final char LoggingIdBegin = 123;
  public static final char LoggingIdEnd = 125;
  public static final char LoggingIdSep = 32;
  public static final String TypeException = ":exception:";
  public static final String TypeService = ":service:";
  public static final byte Status_None = 0;
  public static final byte Status_Tracing = 1;
  public static final byte Status_Batch_Logging = 2;
  public static final byte Status_NetManLog = 4;
  public static final byte Status_TipsOnly = 8;
  public static final byte Status_Interrupted = 16;
  private final Level level;
  private final InternalLogger logger;
  private byte status = 0;


  private StackTraceElement callingStackElement = null;


  private String string = null;

  LoggingParameter(Level level, InternalLogger logger) {
    this.level = level;
    this.logger = logger;
  }

  public Level getLevel() {
    return this.level;
  }

  public InternalLogger getLogger()
  {
    return this.logger;
  }

  

  public void setBatchLogging()
  {
    this.status = (byte)(this.status | 0x2);
  }

  public boolean isBatchLogging() {
    int val = this.status & 0x2;
    return (val > 0);
  }

  public void setNetManLog() {
    this.status = (byte)(this.status | 0x4);
  }

  public boolean isNetManLog() {
    int val = this.status & 0x4;
    return (val > 0);
  }

  public void setTipsLog() {
    this.status = (byte)(this.status | 0x8);
  }

  public boolean isTipsLog() {
    int val = this.status & 0x8;
    return (val > 0);
  }

  public void setInterrupted() {
    this.status = (byte)(this.status | 0x10);
  }

  public boolean isInterrupted() {
    int val = this.status & 0x10;
    return (val > 0);
  }

  public byte getStatus() {
    return this.status;
  }

 

  public void markStackElement(StackTraceElement callingStackElement) {
    this.callingStackElement = callingStackElement;
  }

  public StackTraceElement getCallingStackElement() {
    return this.callingStackElement;
  }

  

  public String toString() {
    if (this.string == null) {
      this.string = toStringInternal();
    }
    return this.string;
  }

  protected String toStringInternal() {
    return "";
  }
}