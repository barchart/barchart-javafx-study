package com.sun.media.jfxmedia.effects;

public abstract interface AudioSpectrum
{
  public abstract boolean getEnabled();

  public abstract void setEnabled(boolean paramBoolean);

  public abstract int getBandCount();

  public abstract void setBandCount(int paramInt);

  public abstract double getInterval();

  public abstract void setInterval(double paramDouble);

  public abstract int getSensitivityThreshold();

  public abstract void setSensitivityThreshold(int paramInt);

  public abstract float[] getMagnitudes();

  public abstract float[] getPhases();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.effects.AudioSpectrum
 * JD-Core Version:    0.6.2
 */