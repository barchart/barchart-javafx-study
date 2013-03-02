package com.sun.javafx.image;

import java.nio.IntBuffer;

public abstract interface IntPixelSetter extends PixelSetter<IntBuffer>
{
  public abstract void setArgb(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public abstract void setArgbPre(int[] paramArrayOfInt, int paramInt1, int paramInt2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.IntPixelSetter
 * JD-Core Version:    0.6.2
 */