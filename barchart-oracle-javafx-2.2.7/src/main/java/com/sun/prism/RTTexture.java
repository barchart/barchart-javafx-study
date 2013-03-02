package com.sun.prism;

import java.nio.Buffer;

public abstract interface RTTexture extends Texture, RenderTarget
{
  public abstract int[] getPixels();

  public abstract boolean readPixels(Buffer paramBuffer);

  public abstract boolean isVolatile();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.RTTexture
 * JD-Core Version:    0.6.2
 */