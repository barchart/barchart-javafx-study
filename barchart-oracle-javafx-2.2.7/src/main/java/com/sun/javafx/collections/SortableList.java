package com.sun.javafx.collections;

import java.util.Comparator;
import java.util.List;

public abstract interface SortableList<E> extends List<E>
{
  public abstract void sort();

  public abstract void sort(Comparator<? super E> paramComparator);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.SortableList
 * JD-Core Version:    0.6.2
 */