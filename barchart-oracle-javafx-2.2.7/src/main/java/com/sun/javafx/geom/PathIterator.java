package com.sun.javafx.geom;

public abstract interface PathIterator
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

  public abstract int currentSegment(float[] paramArrayOfFloat);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.PathIterator
 * JD-Core Version:    0.6.2
 */