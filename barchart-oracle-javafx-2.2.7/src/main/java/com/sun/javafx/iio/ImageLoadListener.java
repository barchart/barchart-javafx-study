package com.sun.javafx.iio;

public abstract interface ImageLoadListener
{
  public abstract void imageLoadProgress(ImageLoader paramImageLoader, float paramFloat);

  public abstract void imageLoadWarning(ImageLoader paramImageLoader, String paramString);

  public abstract void imageLoadMetaData(ImageLoader paramImageLoader, ImageMetadata paramImageMetadata);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageLoadListener
 * JD-Core Version:    0.6.2
 */