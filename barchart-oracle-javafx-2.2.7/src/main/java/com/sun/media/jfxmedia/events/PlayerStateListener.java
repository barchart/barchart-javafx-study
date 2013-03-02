package com.sun.media.jfxmedia.events;

public abstract interface PlayerStateListener
{
  public abstract void onReady(PlayerStateEvent paramPlayerStateEvent);

  public abstract void onPlaying(PlayerStateEvent paramPlayerStateEvent);

  public abstract void onPause(PlayerStateEvent paramPlayerStateEvent);

  public abstract void onStop(PlayerStateEvent paramPlayerStateEvent);

  public abstract void onStall(PlayerStateEvent paramPlayerStateEvent);

  public abstract void onFinish(PlayerStateEvent paramPlayerStateEvent);

  public abstract void onHalt(PlayerStateEvent paramPlayerStateEvent);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.events.PlayerStateListener
 * JD-Core Version:    0.6.2
 */