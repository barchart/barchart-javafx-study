package com.sun.javafx.tk;

public abstract interface TKStageListener
{
  public abstract void changedLocation(float paramFloat1, float paramFloat2);

  public abstract void changedSize(float paramFloat1, float paramFloat2);

  public abstract void changedFocused(boolean paramBoolean, FocusCause paramFocusCause);

  public abstract void changedIconified(boolean paramBoolean);

  public abstract void changedResizable(boolean paramBoolean);

  public abstract void changedFullscreen(boolean paramBoolean);

  public abstract void closing();

  public abstract void closed();

  public abstract void focusUngrab();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.TKStageListener
 * JD-Core Version:    0.6.2
 */