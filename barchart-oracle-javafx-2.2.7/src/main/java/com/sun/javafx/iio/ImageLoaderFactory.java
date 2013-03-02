package com.sun.javafx.iio;

import java.io.IOException;
import java.io.InputStream;

public abstract interface ImageLoaderFactory
{
  public abstract ImageFormatDescription getFormatDescription();

  public abstract ImageLoader createImageLoader(InputStream paramInputStream)
    throws IOException;
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageLoaderFactory
 * JD-Core Version:    0.6.2
 */