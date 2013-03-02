package com.sun.prism;

import com.sun.glass.ui.Screen;

public abstract interface RenderTarget extends Surface
{
  public abstract long getNativeDestHandle();

  public abstract Screen getAssociatedScreen();

  public abstract Graphics createGraphics();

  public abstract boolean isOpaque();

  public abstract void setOpaque(boolean paramBoolean);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.RenderTarget
 * JD-Core Version:    0.6.2
 */