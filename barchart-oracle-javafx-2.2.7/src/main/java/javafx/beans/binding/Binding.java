package javafx.beans.binding;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public abstract interface Binding<T> extends ObservableValue<T>
{
  public abstract boolean isValid();

  public abstract void invalidate();

  public abstract ObservableList<?> getDependencies();

  public abstract void dispose();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.Binding
 * JD-Core Version:    0.6.2
 */