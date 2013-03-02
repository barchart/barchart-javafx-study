package com.sun.glass.ui.delegate;

public abstract interface MenuBarDelegate
{
  public abstract boolean createMenuBar();

  public abstract boolean insert(MenuDelegate paramMenuDelegate, int paramInt);

  public abstract boolean remove(MenuDelegate paramMenuDelegate, int paramInt);

  public abstract long getNativeMenu();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.delegate.MenuBarDelegate
 * JD-Core Version:    0.6.2
 */