package com.sun.javafx.logging;

import java.util.List;

public abstract interface LoggingProxy
{
  public abstract Object getLogger(String paramString);

  public abstract Object getLevel(Object paramObject);

  public abstract void setLevel(Object paramObject1, Object paramObject2);

  public abstract boolean isLoggable(Object paramObject1, Object paramObject2);

  public abstract void log(Object paramObject1, Object paramObject2, String paramString);

  public abstract void log(Object paramObject1, Object paramObject2, String paramString, Throwable paramThrowable);

  public abstract void log(Object paramObject1, Object paramObject2, String paramString, Object[] paramArrayOfObject);

  public abstract List<String> getLoggerNames();

  public abstract String getLoggerLevel(String paramString);

  public abstract void setLoggerLevel(String paramString1, String paramString2);

  public abstract String getParentLoggerName(String paramString);

  public abstract Object parseLevel(String paramString);

  public abstract String getLevelName(Object paramObject);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.logging.LoggingProxy
 * JD-Core Version:    0.6.2
 */