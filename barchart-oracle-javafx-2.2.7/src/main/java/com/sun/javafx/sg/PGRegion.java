package com.sun.javafx.sg;

public abstract interface PGRegion extends PGGroup
{
  public abstract void setBorders(Border[] paramArrayOfBorder);

  public abstract void setBackgroundImages(BackgroundImage[] paramArrayOfBackgroundImage);

  public abstract void setBackgroundFills(BackgroundFill[] paramArrayOfBackgroundFill);

  public abstract void setShape(PGShape paramPGShape);

  public abstract void setResizeShape(boolean paramBoolean);

  public abstract void setPositionShape(boolean paramBoolean);

  public abstract void setSize(float paramFloat1, float paramFloat2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGRegion
 * JD-Core Version:    0.6.2
 */