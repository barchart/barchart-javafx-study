package com.sun.javafx.image;

import java.nio.ByteBuffer;

public abstract interface BytePixelGetter extends PixelGetter<ByteBuffer>
{
  public abstract int getArgb(byte[] paramArrayOfByte, int paramInt);

  public abstract int getArgbPre(byte[] paramArrayOfByte, int paramInt);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.BytePixelGetter
 * JD-Core Version:    0.6.2
 */