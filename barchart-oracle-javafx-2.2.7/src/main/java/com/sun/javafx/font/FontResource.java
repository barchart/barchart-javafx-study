package com.sun.javafx.font;

import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.t2k.CharToGlyphMapper;
import java.lang.ref.WeakReference;
import java.util.Map;

public abstract interface FontResource
{
  public static final int AA_GREYSCALE = 0;
  public static final int AA_LCD = 1;

  public abstract String getFullName();

  public abstract String getPSName();

  public abstract String getFamilyName();

  public abstract String getFileName();

  public abstract String getStyleName();

  public abstract String getLocaleFullName();

  public abstract String getLocaleFamilyName();

  public abstract String getLocaleStyleName();

  public abstract int getNumGlyphs();

  public abstract boolean isBold();

  public abstract boolean isItalic();

  public abstract float getAdvance(int paramInt, float paramFloat);

  public abstract float[] getGlyphBoundingBox(int paramInt, float paramFloat, float[] paramArrayOfFloat);

  public abstract int getDefaultAAMode();

  public abstract CharToGlyphMapper getGlyphMapper();

  public abstract Map<FontStrikeDesc, WeakReference<FontStrike>> getStrikeMap();

  public abstract FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform);

  public abstract FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform);

  public abstract FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform, int paramInt);

  public abstract FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform, int paramInt);

  public abstract Object getPeer();

  public abstract void setPeer(Object paramObject);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.FontResource
 * JD-Core Version:    0.6.2
 */