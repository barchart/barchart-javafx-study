package com.sun.javafx.font;

import com.sun.javafx.geom.transform.BaseTransform;

public abstract interface PGFont
{
  public abstract boolean supportsGlyphImages();

  public abstract String getFullName();

  public abstract String getFamilyName();

  public abstract String getStyleName();

  public abstract String getName();

  public abstract float getSize();

  public abstract int getNumGlyphs();

  public abstract FontStrike getStrike(BaseTransform paramBaseTransform, int paramInt);

  public abstract FontResource getFontResource();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.PGFont
 * JD-Core Version:    0.6.2
 */