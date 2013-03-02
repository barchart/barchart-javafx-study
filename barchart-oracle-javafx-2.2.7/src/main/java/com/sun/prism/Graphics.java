package com.sun.prism;

import com.sun.glass.ui.Screen;
import com.sun.javafx.font.FontStrike;
import com.sun.javafx.geom.RectBounds;
import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.Shape;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.geom.transform.GeneralTransform3D;
import com.sun.prism.camera.PrismCameraImpl;
import com.sun.prism.paint.Color;
import com.sun.prism.paint.Paint;

public abstract interface Graphics
{
  public static final int ISOLATE_NONE = 0;
  public static final int ISOLATE_TOP = 1;
  public static final int ISOLATE_LEFT = 2;
  public static final int ISOLATE_RIGHT = 4;
  public static final int ISOLATE_BOTTOM = 8;
  public static final int ISOLATE_ALL = 15;

  public abstract BaseTransform getTransformNoClone();

  public abstract void setTransform(BaseTransform paramBaseTransform);

  public abstract void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);

  public abstract void setTransform3D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12);

  public abstract void transform(BaseTransform paramBaseTransform);

  public abstract void translate(float paramFloat1, float paramFloat2);

  public abstract void translate(float paramFloat1, float paramFloat2, float paramFloat3);

  public abstract void scale(float paramFloat1, float paramFloat2);

  public abstract void scale(float paramFloat1, float paramFloat2, float paramFloat3);

  public abstract void setWindowProjViewTx(GeneralTransform3D paramGeneralTransform3D);

  public abstract GeneralTransform3D getWindowProjViewTxNoClone();

  public abstract boolean hasOrthoCamera();

  public abstract void setCamera(PrismCameraImpl paramPrismCameraImpl);

  public abstract PrismCameraImpl getCameraNoClone();

  public abstract void setDepthTest(boolean paramBoolean);

  public abstract boolean isDepthTest();

  public abstract void setDepthBuffer(boolean paramBoolean);

  public abstract boolean isDepthBuffer();

  public abstract RectBounds getFinalClipNoClone();

  public abstract Rectangle getClipRect();

  public abstract Rectangle getClipRectNoClone();

  public abstract void setHasPreCullingBits(boolean paramBoolean);

  public abstract boolean hasPreCullingBits();

  public abstract void setClipRect(Rectangle paramRectangle);

  public abstract void setClipRectIndex(int paramInt);

  public abstract int getClipRectIndex();

  public abstract float getExtraAlpha();

  public abstract void setExtraAlpha(float paramFloat);

  public abstract Paint getPaint();

  public abstract void setPaint(Paint paramPaint);

  public abstract BasicStroke getStroke();

  public abstract void setStroke(BasicStroke paramBasicStroke);

  public abstract void setCompositeMode(CompositeMode paramCompositeMode);

  public abstract CompositeMode getCompositeMode();

  public abstract void clear();

  public abstract void clear(Color paramColor);

  public abstract void clearQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void fill(Shape paramShape);

  public abstract void fillQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void fillRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void fillRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  public abstract void fillEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void draw(Shape paramShape);

  public abstract void drawLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void drawRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void drawRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  public abstract void drawEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void setNodeBounds(RectBounds paramRectBounds);

  public abstract void drawString(String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2);

  public abstract void drawString(String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, Color paramColor, int paramInt1, int paramInt2);

  public abstract void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8);

  public abstract void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt);

  public abstract void drawTextureVO(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, int paramInt);

  public abstract void drawTextureRaw(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8);

  public abstract void drawMappedTextureRaw(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12);

  public abstract void sync();

  public abstract void reset();

  public abstract Screen getAssociatedScreen();

  public abstract ResourceFactory getResourceFactory();

  public abstract RenderTarget getRenderTarget();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.Graphics
 * JD-Core Version:    0.6.2
 */