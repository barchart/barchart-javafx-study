package com.sun.javafx.jmx;

import javafx.scene.Node;
import javafx.scene.Parent;

public abstract interface MXNodeAlgorithm
{
  public abstract Object processLeafNode(Node paramNode, MXNodeAlgorithmContext paramMXNodeAlgorithmContext);

  public abstract Object processContainerNode(Parent paramParent, MXNodeAlgorithmContext paramMXNodeAlgorithmContext);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.jmx.MXNodeAlgorithm
 * JD-Core Version:    0.6.2
 */