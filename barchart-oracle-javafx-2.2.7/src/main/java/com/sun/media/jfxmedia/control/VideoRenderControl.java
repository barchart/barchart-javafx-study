package com.sun.media.jfxmedia.control;

import com.sun.media.jfxmedia.events.VideoFrameRateListener;
import com.sun.media.jfxmedia.events.VideoRendererListener;

public abstract interface VideoRenderControl
{
  public abstract void addVideoRendererListener(VideoRendererListener paramVideoRendererListener);

  public abstract void removeVideoRendererListener(VideoRendererListener paramVideoRendererListener);

  public abstract void addVideoFrameRateListener(VideoFrameRateListener paramVideoFrameRateListener);

  public abstract void removeVideoFrameRateListener(VideoFrameRateListener paramVideoFrameRateListener);

  public abstract int getFrameWidth();

  public abstract int getFrameHeight();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.control.VideoRenderControl
 * JD-Core Version:    0.6.2
 */