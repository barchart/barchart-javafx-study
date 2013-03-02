package com.sun.javafx.menu;

import javafx.beans.property.BooleanProperty;

public abstract interface RadioMenuItemBase extends MenuItemBase
{
  public abstract void setSelected(boolean paramBoolean);

  public abstract boolean isSelected();

  public abstract BooleanProperty selectedProperty();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.menu.RadioMenuItemBase
 * JD-Core Version:    0.6.2
 */