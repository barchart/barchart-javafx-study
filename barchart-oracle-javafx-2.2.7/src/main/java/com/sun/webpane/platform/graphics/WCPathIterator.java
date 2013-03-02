package com.sun.webpane.platform.graphics;

public abstract interface WCPathIterator
{
  public static final int WIND_EVEN_ODD = 0;
  public static final int WIND_NON_ZERO = 1;
  public static final int SEG_MOVETO = 0;
  public static final int SEG_LINETO = 1;
  public static final int SEG_QUADTO = 2;
  public static final int SEG_CUBICTO = 3;
  public static final int SEG_CLOSE = 4;

  public abstract int getWindingRule();

  public abstract boolean isDone();

  public abstract void next();

  public abstract int currentSegment(double[] paramArrayOfDouble);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCPathIterator
 * JD-Core Version:    0.6.2
 */