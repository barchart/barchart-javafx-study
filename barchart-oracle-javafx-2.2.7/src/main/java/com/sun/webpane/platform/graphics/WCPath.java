package com.sun.webpane.platform.graphics;

public abstract class WCPath<P> extends Ref
{
  public static final int RULE_NONZERO = 0;
  public static final int RULE_EVENODD = 1;

  public abstract void addRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public abstract void addEllipse(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public abstract void addArcTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5);

  public abstract void addArc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, boolean paramBoolean);

  public abstract boolean contains(int paramInt, double paramDouble1, double paramDouble2);

  public abstract WCRectangle getBounds();

  public abstract void clear();

  public abstract void moveTo(double paramDouble1, double paramDouble2);

  public abstract void addLineTo(double paramDouble1, double paramDouble2);

  public abstract void addQuadCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public abstract void addBezierCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);

  public abstract void addPath(WCPath paramWCPath);

  public abstract void closeSubpath();

  public abstract boolean hasCurrentPoint();

  public abstract boolean isEmpty();

  public abstract void translate(double paramDouble1, double paramDouble2);

  public abstract void transform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);

  public abstract int getWindingRule();

  public abstract void setWindingRule(int paramInt);

  public abstract P getPlatformPath();

  public abstract WCPathIterator getPathIterator();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCPath
 * JD-Core Version:    0.6.2
 */