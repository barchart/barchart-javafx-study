package com.sun.webpane.platform.graphics;

import java.nio.ByteBuffer;

public abstract class WCGraphicsContext
{
  public static final int COMPOSITE_CLEAR = 0;
  public static final int COMPOSITE_COPY = 1;
  public static final int COMPOSITE_SOURCE_OVER = 2;
  public static final int COMPOSITE_SOURCE_IN = 3;
  public static final int COMPOSITE_SOURCE_OUT = 4;
  public static final int COMPOSITE_SOURCE_ATOP = 5;
  public static final int COMPOSITE_DESTINATION_OVER = 6;
  public static final int COMPOSITE_DESTINATION_IN = 7;
  public static final int COMPOSITE_DESTINATION_OUT = 8;
  public static final int COMPOSITE_DESTINATION_ATOP = 9;
  public static final int COMPOSITE_XOR = 10;
  public static final int COMPOSITE_PLUS_DARKER = 11;
  public static final int COMPOSITE_HIGHLIGHT = 12;
  public static final int COMPOSITE_PLUS_LIGHTER = 13;

  public abstract void fillRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Integer paramInteger);

  public abstract void clearRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void setFillColor(int paramInt);

  public abstract void setFillGradient(WCGradient paramWCGradient);

  public abstract void fillRoundedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, int paramInt);

  public abstract void setTextMode(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);

  public abstract void setFontSmoothingType(int paramInt);

  public abstract void setStrokeStyle(int paramInt);

  public abstract void setStrokeColor(int paramInt);

  public abstract void setStrokeWidth(float paramFloat);

  public abstract void setStrokeGradient(WCGradient paramWCGradient);

  public abstract void setLineDash(float paramFloat, float[] paramArrayOfFloat);

  public abstract void setLineCap(int paramInt);

  public abstract void setLineJoin(int paramInt);

  public abstract void setMiterLimit(float paramFloat);

  public abstract void drawPolygon(float[] paramArrayOfFloat, boolean paramBoolean);

  public abstract void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void drawImage(WCImage paramWCImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8);

  public abstract void drawIcon(WCIcon paramWCIcon, int paramInt1, int paramInt2);

  public abstract void drawPattern(WCImage paramWCImage, WCRectangle paramWCRectangle1, WCTransform paramWCTransform, WCPoint paramWCPoint, WCRectangle paramWCRectangle2);

  public abstract void drawBitmapImage(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void translate(float paramFloat1, float paramFloat2);

  public abstract void scale(float paramFloat1, float paramFloat2);

  public abstract void rotate(float paramFloat);

  public abstract void setTransform(WCTransform paramWCTransform);

  public abstract WCTransform getTransform();

  public abstract void concatTransform(WCTransform paramWCTransform);

  public abstract void saveState();

  public abstract void restoreState();

  public abstract void setClip(WCPath paramWCPath, boolean paramBoolean);

  public abstract void setClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void setClip(WCRectangle paramWCRectangle);

  public abstract WCRectangle getClip();

  public abstract void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void setComposite(int paramInt);

  public abstract void strokeArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public abstract void drawEllipse(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void drawFocusRing(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public abstract void setAlpha(float paramFloat);

  public abstract float getAlpha();

  public abstract void beginTransparencyLayer(float paramFloat);

  public abstract void endTransparencyLayer();

  public abstract void strokePath(WCPath paramWCPath);

  public abstract void strokeRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5);

  public abstract void fillPath(WCPath paramWCPath);

  public abstract void setShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt);

  public abstract void drawString(WCFont paramWCFont, String paramString, boolean paramBoolean, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void drawString(WCFont paramWCFont, int[] paramArrayOfInt, float[] paramArrayOfFloat, float paramFloat1, float paramFloat2);

  public abstract void drawWidget(RenderTheme paramRenderTheme, Ref paramRef, int paramInt1, int paramInt2);

  public abstract void drawScrollbar(ScrollBarTheme paramScrollBarTheme, Ref paramRef, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract WCImage getImage();

  public abstract Object getPlatformGraphics();

  public abstract WCGradient createLinearGradient(WCPoint paramWCPoint1, WCPoint paramWCPoint2);

  public abstract WCGradient createRadialGradient(WCPoint paramWCPoint1, float paramFloat1, WCPoint paramWCPoint2, float paramFloat2);

  public abstract void dispose();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCGraphicsContext
 * JD-Core Version:    0.6.2
 */