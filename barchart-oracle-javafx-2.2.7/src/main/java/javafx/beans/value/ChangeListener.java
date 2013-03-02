package javafx.beans.value;

public abstract interface ChangeListener<T>
{
  public abstract void changed(ObservableValue<? extends T> paramObservableValue, T paramT1, T paramT2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.value.ChangeListener
 * JD-Core Version:    0.6.2
 */