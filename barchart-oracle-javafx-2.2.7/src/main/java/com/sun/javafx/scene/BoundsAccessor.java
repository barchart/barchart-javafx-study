package com.sun.javafx.scene;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.scene.Node;

public abstract interface BoundsAccessor
{
  public abstract BaseBounds getGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.BoundsAccessor
 * JD-Core Version:    0.6.2
 */