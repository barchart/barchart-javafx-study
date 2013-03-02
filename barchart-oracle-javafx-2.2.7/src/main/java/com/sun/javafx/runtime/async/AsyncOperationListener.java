package com.sun.javafx.runtime.async;

public abstract interface AsyncOperationListener<V>
{
  public abstract void onProgress(int paramInt1, int paramInt2);

  public abstract void onCompletion(V paramV);

  public abstract void onCancel();

  public abstract void onException(Exception paramException);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.async.AsyncOperationListener
 * JD-Core Version:    0.6.2
 */