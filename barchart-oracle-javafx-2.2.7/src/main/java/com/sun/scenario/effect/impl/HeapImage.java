package com.sun.scenario.effect.impl;

import com.sun.scenario.effect.Filterable;

public abstract interface HeapImage extends Filterable
{
  public abstract int getScanlineStride();

  public abstract int[] getPixelArray();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.HeapImage
 * JD-Core Version:    0.6.2
 */