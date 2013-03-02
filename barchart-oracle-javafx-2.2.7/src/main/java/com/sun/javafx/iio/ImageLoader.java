package com.sun.javafx.iio;

import java.io.IOException;

public abstract interface ImageLoader
{
  public abstract ImageFormatDescription getFormatDescription();

  public abstract void dispose();

  public abstract void addListener(ImageLoadListener paramImageLoadListener);

  public abstract void removeListener(ImageLoadListener paramImageLoadListener);

  public abstract ImageFrame load(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException;
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageLoader
 * JD-Core Version:    0.6.2
 */