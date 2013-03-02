package javafx.beans.value;

import javafx.beans.Observable;

public abstract interface ObservableValue<T> extends Observable
{
  public abstract void addListener(ChangeListener<? super T> paramChangeListener);

  public abstract void removeListener(ChangeListener<? super T> paramChangeListener);

  public abstract T getValue();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.value.ObservableValue
 * JD-Core Version:    0.6.2
 */