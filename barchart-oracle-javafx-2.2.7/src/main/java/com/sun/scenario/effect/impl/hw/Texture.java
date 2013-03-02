package com.sun.scenario.effect.impl.hw;

import com.sun.javafx.geom.Rectangle;

public abstract interface Texture
{
  public abstract Rectangle getNativeBounds();

  public abstract long getNativeSourceHandle();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.hw.Texture
 * JD-Core Version:    0.6.2
 */