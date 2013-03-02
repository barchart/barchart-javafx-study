package javafx.scene.image;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javafx.scene.paint.Color;

public abstract interface PixelReader
{
  public abstract PixelFormat getPixelFormat();

  public abstract int getArgb(int paramInt1, int paramInt2);

  public abstract Color getColor(int paramInt1, int paramInt2);

  public abstract <T extends Buffer> void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<T> paramWritablePixelFormat, T paramT, int paramInt5);

  public abstract void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<ByteBuffer> paramWritablePixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6);

  public abstract void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<IntBuffer> paramWritablePixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.image.PixelReader
 * JD-Core Version:    0.6.2
 */