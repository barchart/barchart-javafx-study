package com.sun.javafx.image;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract interface ByteToIntPixelConverter extends PixelConverter<ByteBuffer, IntBuffer>
{
  public abstract void convert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.ByteToIntPixelConverter
 * JD-Core Version:    0.6.2
 */