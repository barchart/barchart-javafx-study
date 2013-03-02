package com.sun.javafx.image;

import java.nio.IntBuffer;

public abstract interface IntToIntPixelConverter extends PixelConverter<IntBuffer, IntBuffer>
{
  public abstract void convert(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(int[] paramArrayOfInt, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.IntToIntPixelConverter
 * JD-Core Version:    0.6.2
 */