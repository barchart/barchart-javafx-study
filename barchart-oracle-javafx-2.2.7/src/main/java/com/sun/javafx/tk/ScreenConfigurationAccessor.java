package com.sun.javafx.tk;

public abstract interface ScreenConfigurationAccessor
{
  public abstract int getMinX(Object paramObject);

  public abstract int getMinY(Object paramObject);

  public abstract int getWidth(Object paramObject);

  public abstract int getHeight(Object paramObject);

  public abstract int getVisualMinX(Object paramObject);

  public abstract int getVisualMinY(Object paramObject);

  public abstract int getVisualHeight(Object paramObject);

  public abstract int getVisualWidth(Object paramObject);

  public abstract float getDPI(Object paramObject);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.ScreenConfigurationAccessor
 * JD-Core Version:    0.6.2
 */