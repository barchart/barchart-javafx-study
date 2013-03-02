package com.sun.javafx.tk;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

public abstract interface PlatformImage
{
  public abstract int getArgb(int paramInt1, int paramInt2);

  public abstract void setArgb(int paramInt1, int paramInt2, int paramInt3);

  public abstract PixelFormat getPlatformPixelFormat();

  public abstract boolean isWritable();

  public abstract PlatformImage promoteToWritableImage();

  public abstract <T extends Buffer> void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<T> paramWritablePixelFormat, T paramT, int paramInt5);

  public abstract void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<ByteBuffer> paramWritablePixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6);

  public abstract void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<IntBuffer> paramWritablePixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6);

  public abstract <T extends Buffer> void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelFormat<T> paramPixelFormat, T paramT, int paramInt5);

  public abstract void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelFormat<ByteBuffer> paramPixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6);

  public abstract void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelFormat<IntBuffer> paramPixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6);

  public abstract void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelReader paramPixelReader, int paramInt5, int paramInt6);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.PlatformImage
 * JD-Core Version:    0.6.2
 */