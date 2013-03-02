package com.sun.glass.ui.delegate;

import com.sun.glass.ui.Pixels;

public abstract interface MenuDelegate
{
  public abstract boolean createMenu(String paramString, boolean paramBoolean);

  public abstract boolean setTitle(String paramString);

  public abstract boolean setEnabled(boolean paramBoolean);

  public abstract boolean setPixels(Pixels paramPixels);

  public abstract boolean insert(MenuDelegate paramMenuDelegate, int paramInt);

  public abstract boolean insert(MenuItemDelegate paramMenuItemDelegate, int paramInt);

  public abstract boolean remove(MenuDelegate paramMenuDelegate, int paramInt);

  public abstract boolean remove(MenuItemDelegate paramMenuItemDelegate, int paramInt);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.delegate.MenuDelegate
 * JD-Core Version:    0.6.2
 */