package com.sun.prism.impl.shape;

import com.sun.javafx.geom.RectBounds;
import com.sun.javafx.geom.Shape;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.prism.BasicStroke;

public abstract interface ShapeRasterizer
{
  public abstract MaskData getMaskData(Shape paramShape, BasicStroke paramBasicStroke, RectBounds paramRectBounds, BaseTransform paramBaseTransform, boolean paramBoolean);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.ShapeRasterizer
 * JD-Core Version:    0.6.2
 */