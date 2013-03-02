package com.sun.javafx.scene.traversal;

import javafx.scene.Node;

public abstract interface Algorithm
{
  public abstract Node traverse(Node paramNode, Direction paramDirection, TraversalEngine paramTraversalEngine);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.traversal.Algorithm
 * JD-Core Version:    0.6.2
 */