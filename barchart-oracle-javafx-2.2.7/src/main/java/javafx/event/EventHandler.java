package javafx.event;

import java.util.EventListener;

public abstract interface EventHandler<T extends Event> extends EventListener
{
  public abstract void handle(T paramT);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.event.EventHandler
 * JD-Core Version:    0.6.2
 */