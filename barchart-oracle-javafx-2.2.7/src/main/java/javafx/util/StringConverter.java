package javafx.util;

public abstract class StringConverter<T>
{
  public abstract String toString(T paramT);

  public abstract T fromString(String paramString);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.StringConverter
 * JD-Core Version:    0.6.2
 */