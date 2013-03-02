package com.sun.javafx.geom;

public abstract interface PathConsumer2D
{
  public abstract void moveTo(float paramFloat1, float paramFloat2);

  public abstract void lineTo(float paramFloat1, float paramFloat2);

  public abstract void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  public abstract void closePath();

  public abstract void pathDone();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.PathConsumer2D
 * JD-Core Version:    0.6.2
 */