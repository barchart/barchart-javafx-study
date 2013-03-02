package com.sun.javafx.runtime.async;

public abstract interface AsyncOperation
{
  public abstract void start();

  public abstract void cancel();

  public abstract boolean isCancelled();

  public abstract boolean isDone();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.async.AsyncOperation
 * JD-Core Version:    0.6.2
 */