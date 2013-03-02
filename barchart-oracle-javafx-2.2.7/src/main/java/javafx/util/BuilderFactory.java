package javafx.util;

public abstract interface BuilderFactory
{
  public abstract Builder<?> getBuilder(Class<?> paramClass);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.BuilderFactory
 * JD-Core Version:    0.6.2
 */