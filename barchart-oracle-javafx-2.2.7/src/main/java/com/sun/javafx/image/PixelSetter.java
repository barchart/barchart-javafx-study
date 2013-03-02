package com.sun.javafx.image;

import java.nio.Buffer;

public abstract interface PixelSetter<T extends Buffer>
{
  public abstract AlphaType getAlphaType();

  public abstract int getNumElements();

  public abstract void setArgb(T paramT, int paramInt1, int paramInt2);

  public abstract void setArgbPre(T paramT, int paramInt1, int paramInt2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.PixelSetter
 * JD-Core Version:    0.6.2
 */