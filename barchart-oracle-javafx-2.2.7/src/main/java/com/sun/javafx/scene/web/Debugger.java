package com.sun.javafx.scene.web;

import javafx.util.Callback;

public abstract interface Debugger
{
  public abstract boolean isEnabled();

  public abstract void setEnabled(boolean paramBoolean);

  public abstract void sendMessage(String paramString);

  public abstract Callback<String, Void> getMessageCallback();

  public abstract void setMessageCallback(Callback<String, Void> paramCallback);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.Debugger
 * JD-Core Version:    0.6.2
 */