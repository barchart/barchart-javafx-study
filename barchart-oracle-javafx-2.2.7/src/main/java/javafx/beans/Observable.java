package javafx.beans;

public abstract interface Observable
{
  public abstract void addListener(InvalidationListener paramInvalidationListener);

  public abstract void removeListener(InvalidationListener paramInvalidationListener);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.Observable
 * JD-Core Version:    0.6.2
 */