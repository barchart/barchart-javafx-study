package com.sun.javafx.scene.traversal;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public abstract interface TraverseListener
{
  public abstract void onTraverse(Node paramNode, Bounds paramBounds);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.traversal.TraverseListener
 * JD-Core Version:    0.6.2
 */