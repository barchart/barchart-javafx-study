package com.sun.javafx.tk.desktop;

import java.util.Map;
import javafx.stage.Stage;

public abstract interface AppletWindow
{
  public abstract void setStageOnTop(Stage paramStage);

  public abstract void setBackgroundColor(int paramInt);

  public abstract void setForegroundColor(int paramInt);

  public abstract void setVisible(boolean paramBoolean);

  public abstract void setSize(int paramInt1, int paramInt2);

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract void setPosition(int paramInt1, int paramInt2);

  public abstract int getPositionX();

  public abstract int getPositionY();

  public abstract int getRemoteLayerId();

  public abstract void dispatchEvent(Map paramMap);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.desktop.AppletWindow
 * JD-Core Version:    0.6.2
 */