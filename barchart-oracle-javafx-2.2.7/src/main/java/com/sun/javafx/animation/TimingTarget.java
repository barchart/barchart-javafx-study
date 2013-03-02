package com.sun.javafx.animation;

public abstract interface TimingTarget
{
  public abstract void timingEvent(long paramLong);

  public abstract void begin();

  public abstract void end();

  public abstract void toggle();

  public abstract void pause();

  public abstract void resume();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.animation.TimingTarget
 * JD-Core Version:    0.6.2
 */