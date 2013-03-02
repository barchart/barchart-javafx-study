package com.sun.javafx.event;

import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

public abstract interface EventDispatchTree extends EventDispatchChain
{
  public abstract EventDispatchTree createTree();

  public abstract EventDispatchTree mergeTree(EventDispatchTree paramEventDispatchTree);

  public abstract EventDispatchTree append(EventDispatcher paramEventDispatcher);

  public abstract EventDispatchTree prepend(EventDispatcher paramEventDispatcher);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.EventDispatchTree
 * JD-Core Version:    0.6.2
 */