package com.sun.webpane.platform.graphics;

import java.nio.ByteBuffer;

public abstract class RenderTheme extends Ref
{
  public static final int TEXT_FIELD = 0;
  public static final int BUTTON = 1;
  public static final int CHECK_BOX = 2;
  public static final int RADIO_BUTTON = 3;
  public static final int MENU_LIST = 4;
  public static final int MENU_LIST_BUTTON = 5;
  public static final int SLIDER = 6;
  public static final int PROGRESS_BAR = 7;
  public static final int METER = 8;
  public static final int CHECKED = 1;
  public static final int INDETERMINATE = 2;
  public static final int ENABLED = 4;
  public static final int FOCUSED = 8;
  public static final int PRESSED = 16;
  public static final int HOVERED = 32;
  public static final int READ_ONLY = 64;
  public static final int BACKGROUND = 0;
  public static final int FOREGROUND = 1;

  public abstract Ref createWidget(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, ByteBuffer paramByteBuffer);

  public abstract void drawWidget(WCGraphicsContext paramWCGraphicsContext, Ref paramRef, int paramInt1, int paramInt2);

  public abstract int getRadioButtonSize();

  public abstract int getSelectionColor(int paramInt);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.RenderTheme
 * JD-Core Version:    0.6.2
 */