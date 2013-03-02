package com.sun.media.jfxmedia.effects;

public abstract interface EqualizerBand
{
  public static final double MIN_GAIN = -24.0D;
  public static final double MAX_GAIN = 12.0D;

  public abstract double getCenterFrequency();

  public abstract void setCenterFrequency(double paramDouble);

  public abstract double getBandwidth();

  public abstract void setBandwidth(double paramDouble);

  public abstract double getGain();

  public abstract void setGain(double paramDouble);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.effects.EqualizerBand
 * JD-Core Version:    0.6.2
 */