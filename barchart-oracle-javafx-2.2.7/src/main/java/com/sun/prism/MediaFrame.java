package com.sun.prism;

import java.nio.ByteBuffer;

public abstract interface MediaFrame
{
  public abstract ByteBuffer getBuffer();

  public abstract PixelFormat getPixelFormat();

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract int getEncodedWidth();

  public abstract int getEncodedHeight();

  public abstract int planeCount();

  public abstract int[] planeOffsets();

  public abstract int offsetForPlane(int paramInt);

  public abstract int[] planeStrides();

  public abstract int strideForPlane(int paramInt);

  public abstract MediaFrame convertToFormat(PixelFormat paramPixelFormat);

  public abstract void holdFrame();

  public abstract void releaseFrame();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.MediaFrame
 * JD-Core Version:    0.6.2
 */