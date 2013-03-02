package com.sun.javafx.image;

import java.nio.Buffer;

public abstract interface PixelGetter<T extends Buffer>
{
  public abstract AlphaType getAlphaType();

  public abstract int getNumElements();

  public abstract int getArgb(T paramT, int paramInt);

  public abstract int getArgbPre(T paramT, int paramInt);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.PixelGetter
 * JD-Core Version:    0.6.2
 */