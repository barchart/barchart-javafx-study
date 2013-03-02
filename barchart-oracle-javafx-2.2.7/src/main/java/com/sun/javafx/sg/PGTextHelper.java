package com.sun.javafx.sg;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;

public abstract interface PGTextHelper
{
  public abstract void setLocation(float paramFloat1, float paramFloat2);

  public abstract void setText(String paramString);

  public abstract void setFont(Object paramObject);

  public abstract void setTextBoundsType(int paramInt);

  public abstract void setTextOrigin(int paramInt);

  public abstract void setWrappingWidth(float paramFloat);

  public abstract void setUnderline(boolean paramBoolean);

  public abstract void setStrikethrough(boolean paramBoolean);

  public abstract void setTextAlignment(int paramInt);

  public abstract void setFontSmoothingType(int paramInt);

  public abstract void setCumulativeTransform(BaseTransform paramBaseTransform);

  public abstract void setMode(PGShape.Mode paramMode);

  public abstract void setStroke(boolean paramBoolean);

  public abstract void setStrokeParameters(PGShape.StrokeType paramStrokeType, float[] paramArrayOfFloat, float paramFloat1, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat2, float paramFloat3);

  public abstract void setLogicalSelection(int paramInt1, int paramInt2);

  public abstract void setSelectionPaint(Object paramObject1, Object paramObject2);

  public abstract BaseBounds computeContentBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform);

  public abstract BaseBounds computeLayoutBounds(BaseBounds paramBaseBounds);

  public abstract Object getCaretShape(int paramInt, boolean paramBoolean);

  public abstract Object getSelectionShape();

  public abstract Object getRangeShape(int paramInt1, int paramInt2);

  public abstract Object getUnderlineShape(int paramInt1, int paramInt2);

  public abstract Object getShape();

  public abstract Object getHitInfo(float paramFloat1, float paramFloat2);

  public abstract boolean computeContains(float paramFloat1, float paramFloat2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGTextHelper
 * JD-Core Version:    0.6.2
 */