package com.sun.prism;

import com.sun.javafx.geom.Rectangle;

public abstract interface ReadbackGraphics extends Graphics
{
  public abstract boolean canReadBack();

  public abstract RTTexture readBack(Rectangle paramRectangle);

  public abstract void releaseReadBackBuffer(RTTexture paramRTTexture);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.ReadbackGraphics
 * JD-Core Version:    0.6.2
 */