package com.sun.javafx.image;

import java.nio.ByteBuffer;

public abstract interface ByteToBytePixelConverter extends PixelConverter<ByteBuffer, ByteBuffer>
{
  public abstract void convert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void convert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.ByteToBytePixelConverter
 * JD-Core Version:    0.6.2
 */