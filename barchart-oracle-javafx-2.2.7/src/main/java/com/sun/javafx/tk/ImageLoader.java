package com.sun.javafx.tk;

public abstract interface ImageLoader
{
  public abstract boolean getError();

  public abstract int getFrameCount();

  public abstract PlatformImage[] getFrames();

  public abstract PlatformImage getFrame(int paramInt);

  public abstract int getFrameDelay(int paramInt);

  public abstract int getWidth();

  public abstract int getHeight();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.ImageLoader
 * JD-Core Version:    0.6.2
 */