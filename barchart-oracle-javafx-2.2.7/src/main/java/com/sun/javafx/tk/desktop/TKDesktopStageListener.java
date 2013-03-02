package com.sun.javafx.tk.desktop;

public abstract interface TKDesktopStageListener
{
  public abstract boolean shouldDragStart(Object paramObject);

  public abstract void onDragStarted();

  public abstract void onDragFinished();

  public abstract void onAppletRestored();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.desktop.TKDesktopStageListener
 * JD-Core Version:    0.6.2
 */