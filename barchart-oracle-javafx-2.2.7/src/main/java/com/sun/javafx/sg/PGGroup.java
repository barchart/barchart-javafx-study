package com.sun.javafx.sg;

import java.util.List;

public abstract interface PGGroup extends PGNode
{
  public abstract List<PGNode> getChildren();

  public abstract List<PGNode> getRemovedChildren();

  public abstract void add(int paramInt, PGNode paramPGNode);

  public abstract void remove(PGNode paramPGNode);

  public abstract void remove(int paramInt);

  public abstract void addToRemoved(PGNode paramPGNode);

  public abstract void clear();

  public abstract void clearFrom(int paramInt);

  public abstract void setBlendMode(Object paramObject);

  public abstract void markDirty();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGGroup
 * JD-Core Version:    0.6.2
 */