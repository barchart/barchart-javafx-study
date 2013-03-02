package com.sun.prism;

import com.sun.glass.ui.View;
import com.sun.prism.impl.VertexBuffer;
import com.sun.prism.shape.ShapeRep;

public abstract interface ResourceFactory extends GraphicsResource
{
  public abstract boolean isDeviceReady();

  public abstract Texture createTexture(Image paramImage);

  public abstract Texture createTexture(Image paramImage, Texture.Usage paramUsage, boolean paramBoolean);

  public abstract Texture createTexture(PixelFormat paramPixelFormat, Texture.Usage paramUsage, int paramInt1, int paramInt2);

  public abstract Texture createTexture(PixelFormat paramPixelFormat, Texture.Usage paramUsage, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract Texture createTexture(MediaFrame paramMediaFrame);

  public abstract Texture getCachedTexture(Image paramImage);

  public abstract Texture getCachedTexture(Image paramImage, boolean paramBoolean);

  public abstract boolean isFormatSupported(PixelFormat paramPixelFormat);

  public abstract int getMaximumTextureSize();

  public abstract Texture createMaskTexture(int paramInt1, int paramInt2);

  public abstract Texture createFloatTexture(int paramInt1, int paramInt2);

  public abstract RTTexture createRTTexture(int paramInt1, int paramInt2);

  public abstract Presentable createPresentable(View paramView);

  public abstract VertexBuffer createVertexBuffer(int paramInt);

  public abstract ShapeRep createPathRep(boolean paramBoolean);

  public abstract ShapeRep createRoundRectRep(boolean paramBoolean);

  public abstract ShapeRep createEllipseRep(boolean paramBoolean);

  public abstract ShapeRep createArcRep(boolean paramBoolean);

  public abstract void addFactoryListener(ResourceFactoryListener paramResourceFactoryListener);

  public abstract void removeFactoryListener(ResourceFactoryListener paramResourceFactoryListener);

  public abstract RenderingContext createRenderingContext(View paramView);

  public abstract void dispose();
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.ResourceFactory
 * JD-Core Version:    0.6.2
 */