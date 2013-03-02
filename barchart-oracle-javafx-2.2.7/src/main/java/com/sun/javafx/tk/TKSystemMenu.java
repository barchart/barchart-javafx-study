package com.sun.javafx.tk;

import com.sun.javafx.menu.MenuBase;
import java.util.List;

public abstract interface TKSystemMenu
{
  public abstract boolean isSupported();

  public abstract void setMenus(List<MenuBase> paramList);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKSystemMenu
 * JD-Core Version:    0.6.2
 */