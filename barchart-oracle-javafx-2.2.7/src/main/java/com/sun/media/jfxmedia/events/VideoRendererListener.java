package com.sun.media.jfxmedia.events;

public abstract interface VideoRendererListener
{
  public abstract void videoFrameUpdated(NewFrameEvent paramNewFrameEvent);

  public abstract void releaseVideoFrames();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.events.VideoRendererListener
 * JD-Core Version:    0.6.2
 */