package com.sun.javafx.tk.quantum;

public abstract interface GlassTouchEventListener
{
  public abstract void notifyBeginTouchEvent(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2);

  public abstract void notifyNextTouchEvent(long paramLong1, int paramInt1, long paramLong2, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public abstract void notifyEndTouchEvent(long paramLong);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassTouchEventListener
 * JD-Core Version:    0.6.2
 */