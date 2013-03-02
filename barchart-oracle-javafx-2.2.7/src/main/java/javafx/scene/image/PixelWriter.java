package javafx.scene.image;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javafx.scene.paint.Color;

public abstract interface PixelWriter
{
  public abstract PixelFormat getPixelFormat();

  public abstract void setArgb(int paramInt1, int paramInt2, int paramInt3);

  public abstract void setColor(int paramInt1, int paramInt2, Color paramColor);

  public abstract <T extends Buffer> void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelFormat<T> paramPixelFormat, T paramT, int paramInt5);

  public abstract void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelFormat<ByteBuffer> paramPixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6);

  public abstract void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelFormat<IntBuffer> paramPixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6);

  public abstract void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelReader paramPixelReader, int paramInt5, int paramInt6);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.image.PixelWriter
 * JD-Core Version:    0.6.2
 */