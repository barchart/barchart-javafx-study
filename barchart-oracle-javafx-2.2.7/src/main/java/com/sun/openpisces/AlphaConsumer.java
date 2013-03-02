package com.sun.openpisces;

public abstract interface AlphaConsumer
{
  public abstract int getOriginX();

  public abstract int getOriginY();

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract void setMaxAlpha(int paramInt);

  public abstract void setAndClearRelativeAlphas(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.openpisces.AlphaConsumer
 * JD-Core Version:    0.6.2
 */