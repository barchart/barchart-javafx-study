package com.sun.javafx.iio.common;

import java.nio.ByteBuffer;

public abstract interface PushbroomScaler
{
  public abstract ByteBuffer getDestination();

  public abstract boolean putSourceScanline(byte[] paramArrayOfByte, int paramInt);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.common.PushbroomScaler
 * JD-Core Version:    0.6.2
 */