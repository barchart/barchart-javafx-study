package com.sun.scenario.animation;

public abstract interface AnimationPulseMBean
{
  public abstract boolean getEnabled();

  public abstract void setEnabled(boolean paramBoolean);

  public abstract long getPULSE_DURATION();

  public abstract long getSkippedPulses();

  public abstract long getSkippedPulsesIn1Sec();

  public abstract long getStartMax();

  public abstract long getStartMaxIn1Sec();

  public abstract long getStartAv();

  public abstract long getStartAvIn100Millis();

  public abstract long getEndMax();

  public abstract long getEndMaxIn1Sec();

  public abstract long getEndAv();

  public abstract long getEndAvIn100Millis();

  public abstract long getAnimationDurationMax();

  public abstract long getAnimationMaxIn1Sec();

  public abstract long getAnimationDurationAv();

  public abstract long getAnimationDurationAvIn100Millis();

  public abstract long getPaintingDurationMax();

  public abstract long getPaintingDurationMaxIn1Sec();

  public abstract long getPaintingDurationAv();

  public abstract long getPaitningDurationAvIn100Millis();

  public abstract long getScenePaintingDurationMaxIn1Sec();

  public abstract long getPaintingPreparationDurationMaxIn1Sec();

  public abstract long getPaintingFinalizationDurationMaxIn1Sec();

  public abstract long getPulseDurationMax();

  public abstract long getPulseDurationMaxIn1Sec();

  public abstract long getPulseDurationAv();

  public abstract long getPulseDurationAvIn100Millis();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.AnimationPulseMBean
 * JD-Core Version:    0.6.2
 */