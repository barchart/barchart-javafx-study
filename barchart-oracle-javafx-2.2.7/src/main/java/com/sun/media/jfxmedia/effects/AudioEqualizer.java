package com.sun.media.jfxmedia.effects;

public abstract interface AudioEqualizer
{
  public static final int MAX_NUM_BANDS = 64;

  public abstract boolean getEnabled();

  public abstract void setEnabled(boolean paramBoolean);

  public abstract EqualizerBand addBand(double paramDouble1, double paramDouble2, double paramDouble3);

  public abstract boolean removeBand(double paramDouble);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.effects.AudioEqualizer
 * JD-Core Version:    0.6.2
 */