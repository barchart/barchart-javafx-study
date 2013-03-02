package com.sun.javafx.event;

import java.util.Set;
import javafx.event.EventTarget;

public abstract interface CompositeEventTarget extends EventTarget
{
  public abstract Set<EventTarget> getTargets();

  public abstract boolean containsTarget(EventTarget paramEventTarget);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.CompositeEventTarget
 * JD-Core Version:    0.6.2
 */