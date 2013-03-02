package com.sun.javafx.image;

import java.nio.IntBuffer;

public abstract interface IntPixelGetter extends PixelGetter<IntBuffer>
{
  public abstract int getArgb(int[] paramArrayOfInt, int paramInt);

  public abstract int getArgbPre(int[] paramArrayOfInt, int paramInt);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.IntPixelGetter
 * JD-Core Version:    0.6.2
 */