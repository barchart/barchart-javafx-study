package javafx.collections;

import java.util.Set;
import javafx.beans.Observable;

public abstract interface ObservableSet<E> extends Set<E>, Observable
{
  public abstract void addListener(SetChangeListener<? super E> paramSetChangeListener);

  public abstract void removeListener(SetChangeListener<? super E> paramSetChangeListener);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.ObservableSet
 * JD-Core Version:    0.6.2
 */