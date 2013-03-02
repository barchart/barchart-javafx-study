package com.sun.javafx.image;

import java.nio.ByteBuffer;

public abstract interface BytePixelSetter extends PixelSetter<ByteBuffer>
{
  public abstract void setArgb(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public abstract void setArgbPre(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.BytePixelSetter
 * JD-Core Version:    0.6.2
 */