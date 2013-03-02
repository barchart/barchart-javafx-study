package com.sun.javafx.font;

import java.io.InputStream;

public abstract interface FontFactory
{
  public abstract PGFont createFont(String paramString, float paramFloat);

  public abstract PGFont createFont(String paramString, boolean paramBoolean1, boolean paramBoolean2, float paramFloat);

  public abstract PGFont deriveFont(PGFont paramPGFont, boolean paramBoolean1, boolean paramBoolean2, float paramFloat);

  public abstract String[] getFontFamilyNames();

  public abstract String[] getFontFullNames();

  public abstract String[] getFontFullNames(String paramString);

  public abstract PGFont loadEmbeddedFont(String paramString, InputStream paramInputStream, float paramFloat, boolean paramBoolean);

  public abstract PGFont loadEmbeddedFont(String paramString1, String paramString2, float paramFloat, boolean paramBoolean);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.FontFactory
 * JD-Core Version:    0.6.2
 */