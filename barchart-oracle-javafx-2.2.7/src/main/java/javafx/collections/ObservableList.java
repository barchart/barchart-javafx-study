package javafx.collections;

import java.util.Collection;
import java.util.List;
import javafx.beans.Observable;

public abstract interface ObservableList<E> extends List<E>, Observable
{
  public abstract void addListener(ListChangeListener<? super E> paramListChangeListener);

  public abstract void removeListener(ListChangeListener<? super E> paramListChangeListener);

  public abstract boolean addAll(E[] paramArrayOfE);

  public abstract boolean setAll(E[] paramArrayOfE);

  public abstract boolean setAll(Collection<? extends E> paramCollection);

  public abstract boolean removeAll(E[] paramArrayOfE);

  public abstract boolean retainAll(E[] paramArrayOfE);

  public abstract void remove(int paramInt1, int paramInt2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.ObservableList
 * JD-Core Version:    0.6.2
 */