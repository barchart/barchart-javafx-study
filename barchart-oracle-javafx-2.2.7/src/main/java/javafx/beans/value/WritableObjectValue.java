package javafx.beans.value;

public abstract interface WritableObjectValue<T> extends WritableValue<T>
{
  public abstract T get();

  public abstract void set(T paramT);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.value.WritableObjectValue
 * JD-Core Version:    0.6.2
 */