package com.sun.t2k;

import com.sun.javafx.font.FontResource;

public abstract interface CompositeFontResource extends FontResource
{
  public abstract FontResource getSlotResource(int paramInt);

  public abstract int getNumSlots();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.CompositeFontResource
 * JD-Core Version:    0.6.2
 */