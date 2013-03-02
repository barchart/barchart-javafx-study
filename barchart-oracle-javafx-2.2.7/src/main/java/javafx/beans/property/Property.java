package javafx.beans.property;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;

public abstract interface Property<T> extends ReadOnlyProperty<T>, WritableValue<T>
{
  public abstract void bind(ObservableValue<? extends T> paramObservableValue);

  public abstract void unbind();

  public abstract boolean isBound();

  public abstract void bindBidirectional(Property<T> paramProperty);

  public abstract void unbindBidirectional(Property<T> paramProperty);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.Property
 * JD-Core Version:    0.6.2
 */