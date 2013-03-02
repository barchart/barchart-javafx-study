package com.sun.glass.ui.delegate;

import com.sun.glass.ui.MenuItem.Callback;
import com.sun.glass.ui.Pixels;

public abstract interface MenuItemDelegate
{
  public abstract boolean createMenuItem(String paramString, MenuItem.Callback paramCallback, int paramInt1, int paramInt2, Pixels paramPixels, boolean paramBoolean1, boolean paramBoolean2);

  public abstract boolean setTitle(String paramString);

  public abstract boolean setCallback(MenuItem.Callback paramCallback);

  public abstract boolean setShortcut(int paramInt1, int paramInt2);

  public abstract boolean setPixels(Pixels paramPixels);

  public abstract boolean setEnabled(boolean paramBoolean);

  public abstract boolean setChecked(boolean paramBoolean);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.delegate.MenuItemDelegate
 * JD-Core Version:    0.6.2
 */