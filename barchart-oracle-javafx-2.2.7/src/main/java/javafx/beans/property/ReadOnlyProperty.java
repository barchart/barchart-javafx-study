package javafx.beans.property;

import javafx.beans.value.ObservableValue;

public abstract interface ReadOnlyProperty<T> extends ObservableValue<T>
{
  public abstract Object getBean();

  public abstract String getName();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyProperty
 * JD-Core Version:    0.6.2
 */