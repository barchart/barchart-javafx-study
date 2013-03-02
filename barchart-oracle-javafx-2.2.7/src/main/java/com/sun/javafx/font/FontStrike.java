package com.sun.javafx.font;

import com.sun.javafx.geom.Path2D;
import com.sun.javafx.geom.RectBounds;
import com.sun.javafx.geom.Shape;
import com.sun.javafx.geom.transform.BaseTransform;

public abstract interface FontStrike
{
  public abstract float getStrikeThroughOffset();

  public abstract float getStrikeThroughThickness();

  public abstract float getUnderLineOffset();

  public abstract float getUnderLineThickness();

  public abstract FontResource getFontResource();

  public abstract float getSize();

  public abstract BaseTransform getTransform();

  public abstract boolean drawAsShapes();

  public abstract int getNumGlyphs();

  public abstract boolean supportsGlyphImages();

  public abstract Metrics getMetrics();

  public abstract Glyph getGlyph(char paramChar);

  public abstract Glyph getGlyph(int paramInt);

  public abstract void clearDesc();

  public abstract int getAAMode();

  public abstract float getCharAdvance(char paramChar);

  public abstract float getStringWidth(String paramString);

  public abstract float getStringHeight(String paramString);

  public abstract RectBounds getStringBounds(String paramString);

  public abstract RectBounds getStringVisualBounds(String paramString);

  public abstract Shape getOutline(String paramString, BaseTransform paramBaseTransform);

  public abstract void getOutline(String paramString, BaseTransform paramBaseTransform, Path2D paramPath2D);

  public static abstract interface Glyph
  {
    public abstract int getGlyphCode();

    public abstract RectBounds getBBox();

    public abstract float getAdvance();

    public abstract Shape getShape();

    public abstract byte[] getPixelData();

    public abstract float getPixelXAdvance();

    public abstract float getPixelYAdvance();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getOriginX();

    public abstract int getOriginY();

    public abstract boolean isLCDGlyph();
  }

  public static abstract interface Metrics
  {
    public abstract float getAscent();

    public abstract float getDescent();

    public abstract float getLineGap();

    public abstract float getLineHeight();

    public abstract float getXHeight();
  }
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.FontStrike
 * JD-Core Version:    0.6.2
 */