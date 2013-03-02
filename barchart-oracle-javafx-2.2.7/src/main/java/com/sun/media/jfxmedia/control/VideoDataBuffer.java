package com.sun.media.jfxmedia.control;

import java.nio.ByteBuffer;

public abstract interface VideoDataBuffer
{
  public static final int PACKED_FORMAT_PLANE = 0;
  public static final int YCBCR_PLANE_LUMA = 0;
  public static final int YCBCR_PLANE_CR = 1;
  public static final int YCBCR_PLANE_CB = 2;
  public static final int YCBCR_PLANE_ALPHA = 3;

  public abstract ByteBuffer getBuffer();

  public abstract double getTimestamp();

  public abstract long getFrameNumber();

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract int getEncodedWidth();

  public abstract int getEncodedHeight();

  public abstract VideoFormat getFormat();

  public abstract boolean hasAlpha();

  public abstract int getPlaneCount();

  public abstract int getOffsetForPlane(int paramInt);

  public abstract int[] getPlaneOffsets();

  public abstract int getStrideForPlane(int paramInt);

  public abstract int[] getPlaneStrides();

  public abstract VideoDataBuffer convertToFormat(VideoFormat paramVideoFormat);

  public abstract void setDirty();

  public abstract void holdFrame();

  public abstract void releaseFrame();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.control.VideoDataBuffer
 * JD-Core Version:    0.6.2
 */