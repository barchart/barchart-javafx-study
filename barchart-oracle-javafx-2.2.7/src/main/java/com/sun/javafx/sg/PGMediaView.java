package com.sun.javafx.sg;

public abstract interface PGMediaView extends PGNode
{
  public abstract void renderNextFrame();

  public abstract void setMediaProvider(Object paramObject);

  public abstract void setX(float paramFloat);

  public abstract void setY(float paramFloat);

  public abstract void setViewport(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean);

  public abstract void setSmooth(boolean paramBoolean);

  public abstract void setFrameTracker(MediaFrameTracker paramMediaFrameTracker);

  public static abstract interface MediaFrameTracker
  {
    public abstract void incrementDecodedFrameCount(int paramInt);

    public abstract void incrementRenderedFrameCount(int paramInt);
  }
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGMediaView
 * JD-Core Version:    0.6.2
 */