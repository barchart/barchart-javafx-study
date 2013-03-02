package com.sun.javafx.image;

import java.nio.Buffer;

public abstract interface PixelConverter<T extends Buffer, U extends Buffer>
{
  public abstract void convert(T paramT, int paramInt1, int paramInt2, U paramU, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract PixelGetter<T> getGetter();

  public abstract PixelSetter<U> getSetter();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.PixelConverter
 * JD-Core Version:    0.6.2
 */