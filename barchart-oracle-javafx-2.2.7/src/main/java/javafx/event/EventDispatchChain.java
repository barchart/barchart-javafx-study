package javafx.event;

public abstract interface EventDispatchChain
{
  public abstract EventDispatchChain append(EventDispatcher paramEventDispatcher);

  public abstract EventDispatchChain prepend(EventDispatcher paramEventDispatcher);

  public abstract Event dispatchEvent(Event paramEvent);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.event.EventDispatchChain
 * JD-Core Version:    0.6.2
 */