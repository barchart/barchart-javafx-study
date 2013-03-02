package com.sun.webpane.platform.graphics;

public abstract class WCPageBackBuffer extends Ref
{
  public abstract WCGraphicsContext createGraphics();

  public abstract void disposeGraphics(WCGraphicsContext paramWCGraphicsContext);

  public abstract void flush(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract boolean validate(int paramInt1, int paramInt2);

  public abstract boolean isValid();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCPageBackBuffer
 * JD-Core Version:    0.6.2
 */