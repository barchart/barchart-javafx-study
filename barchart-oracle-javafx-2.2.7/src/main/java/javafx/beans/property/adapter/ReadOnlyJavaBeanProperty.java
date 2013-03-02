package javafx.beans.property.adapter;

import javafx.beans.property.ReadOnlyProperty;

public abstract interface ReadOnlyJavaBeanProperty<T> extends ReadOnlyProperty<T>
{
  public abstract void fireValueChangedEvent();

  public abstract void dispose();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanProperty
 * JD-Core Version:    0.6.2
 */