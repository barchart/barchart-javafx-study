package com.sun.scenario.effect.impl.hw;

import com.sun.scenario.effect.Effect.AccelType;
import java.io.InputStream;

public abstract interface ShaderSource
{
  public abstract InputStream loadSource(String paramString);

  public abstract Effect.AccelType getAccelType();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.hw.ShaderSource
 * JD-Core Version:    0.6.2
 */