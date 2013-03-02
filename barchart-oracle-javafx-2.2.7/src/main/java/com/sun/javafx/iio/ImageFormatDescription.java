package com.sun.javafx.iio;

public abstract interface ImageFormatDescription
{
  public abstract String getFormatName();

  public abstract String[] getExtensions();

  public abstract byte[][] getSignatures();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageFormatDescription
 * JD-Core Version:    0.6.2
 */