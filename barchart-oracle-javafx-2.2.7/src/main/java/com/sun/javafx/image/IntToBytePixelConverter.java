package com.sun.javafx.image;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract interface IntToBytePixelConverter extends PixelConverter<IntBuffer, ByteBuffer>
{
  public abstract void convert(int[] paramArrayOfInt, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(int[] paramArrayOfInt, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.IntToBytePixelConverter
 * JD-Core Version:    0.6.2
 */