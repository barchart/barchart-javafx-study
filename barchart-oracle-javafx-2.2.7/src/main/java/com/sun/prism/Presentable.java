package com.sun.prism;

import com.sun.javafx.geom.Rectangle;

public abstract interface Presentable extends RenderTarget
{
  public abstract boolean prepare(Rectangle paramRectangle);

  public abstract boolean present();

  public abstract boolean recreateOnResize();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.Presentable
 * JD-Core Version:    0.6.2
 */