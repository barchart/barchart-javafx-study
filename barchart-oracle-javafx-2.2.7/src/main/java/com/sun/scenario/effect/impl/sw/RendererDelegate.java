package com.sun.scenario.effect.impl.sw;

import com.sun.scenario.effect.Effect.AccelType;

public abstract interface RendererDelegate
{
  public abstract Effect.AccelType getAccelType();

  public abstract String getPlatformPeerName(String paramString, int paramInt);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.RendererDelegate
 * JD-Core Version:    0.6.2
 */