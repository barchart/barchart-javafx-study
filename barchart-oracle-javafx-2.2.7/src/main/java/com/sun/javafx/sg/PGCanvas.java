package com.sun.javafx.sg;

public abstract interface PGCanvas extends PGNode
{
  public static final byte ATTR_BASE = 0;
  public static final byte GLOBAL_ALPHA = 0;
  public static final byte COMP_MODE = 1;
  public static final byte FILL_PAINT = 2;
  public static final byte STROKE_PAINT = 3;
  public static final byte LINE_WIDTH = 4;
  public static final byte LINE_CAP = 5;
  public static final byte LINE_JOIN = 6;
  public static final byte MITER_LIMIT = 7;
  public static final byte FONT = 8;
  public static final byte TEXT_ALIGN = 9;
  public static final byte TEXT_BASELINE = 10;
  public static final byte TRANSFORM = 11;
  public static final byte EFFECT = 12;
  public static final byte PUSH_CLIP = 13;
  public static final byte POP_CLIP = 14;
  public static final byte ARC_TYPE = 15;
  public static final byte FILL_RULE = 16;
  public static final byte OP_BASE = 20;
  public static final byte FILL_RECT = 20;
  public static final byte STROKE_RECT = 21;
  public static final byte CLEAR_RECT = 22;
  public static final byte STROKE_LINE = 23;
  public static final byte FILL_OVAL = 24;
  public static final byte STROKE_OVAL = 25;
  public static final byte FILL_ROUND_RECT = 26;
  public static final byte STROKE_ROUND_RECT = 27;
  public static final byte FILL_ARC = 28;
  public static final byte STROKE_ARC = 29;
  public static final byte FILL_TEXT = 30;
  public static final byte STROKE_TEXT = 31;
  public static final byte PATH_BASE = 40;
  public static final byte PATHSTART = 40;
  public static final byte MOVETO = 41;
  public static final byte LINETO = 42;
  public static final byte QUADTO = 43;
  public static final byte CUBICTO = 44;
  public static final byte CLOSEPATH = 45;
  public static final byte PATHEND = 46;
  public static final byte FILL_PATH = 47;
  public static final byte STROKE_PATH = 48;
  public static final byte IMG_BASE = 50;
  public static final byte DRAW_IMAGE = 50;
  public static final byte DRAW_SUBIMAGE = 51;
  public static final byte PUT_ARGB = 52;
  public static final byte PUT_ARGBPRE_BUF = 53;
  public static final byte FX_BASE = 60;
  public static final byte FX_APPLY_EFFECT = 60;
  public static final byte CAP_BUTT = 0;
  public static final byte CAP_ROUND = 1;
  public static final byte CAP_SQUARE = 2;
  public static final byte JOIN_MITER = 0;
  public static final byte JOIN_ROUND = 1;
  public static final byte JOIN_BEVEL = 2;
  public static final byte ARC_OPEN = 0;
  public static final byte ARC_CHORD = 1;
  public static final byte ARC_PIE = 2;
  public static final byte ALIGN_LEFT = 0;
  public static final byte ALIGN_CENTER = 1;
  public static final byte ALIGN_RIGHT = 2;
  public static final byte ALIGN_JUSTIFY = 3;
  public static final byte BASE_TOP = 0;
  public static final byte BASE_MIDDLE = 1;
  public static final byte BASE_ALPHABETIC = 2;
  public static final byte BASE_BOTTOM = 3;
  public static final byte FILL_RULE_NON_ZERO = 0;
  public static final byte FILL_RULE_EVEN_ODD = 1;

  public abstract void updateBounds(float paramFloat1, float paramFloat2);

  public abstract void updateRendering(GrowableDataBuffer paramGrowableDataBuffer);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGCanvas
 * JD-Core Version:    0.6.2
 */