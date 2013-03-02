package com.sun.deploy.uitoolkit.impl.fx;

import javafx.stage.Stage;

public abstract interface AppletStageManager
{
  public abstract Stage getAppletStage();

  public abstract Stage getPreloaderStage();

  public abstract Stage getErrorStage();

  public abstract void setSize(int paramInt1, int paramInt2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.AppletStageManager
 * JD-Core Version:    0.6.2
 */