package com.sun.scenario.effect.impl.hw;

import com.sun.scenario.effect.Effect.AccelType;
import com.sun.scenario.effect.FloatMap;
import java.util.Map;

public abstract interface RendererDelegate
{
  public abstract Effect.AccelType getAccelType();

  public abstract void setListener(Listener paramListener);

  public abstract void enable();

  public abstract void disable();

  public abstract void dispose();

  public abstract Shader createShader(String paramString, Map<String, Integer> paramMap1, Map<String, Integer> paramMap2);

  public abstract Texture createFloatTexture(int paramInt1, int paramInt2);

  public abstract void updateFloatTexture(Object paramObject, FloatMap paramFloatMap);

  public abstract void drawQuad(Drawable paramDrawable, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void drawTexture(Drawable paramDrawable, Texture paramTexture, boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8);

  public abstract void drawMappedTexture(Drawable paramDrawable, Texture paramTexture, boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12);

  public abstract void drawTexture(Drawable paramDrawable, Texture paramTexture1, boolean paramBoolean1, Texture paramTexture2, boolean paramBoolean2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12);

  public abstract void drawMappedTexture(Drawable paramDrawable, Texture paramTexture1, boolean paramBoolean1, Texture paramTexture2, boolean paramBoolean2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16, float paramFloat17, float paramFloat18, float paramFloat19, float paramFloat20);

  public static abstract interface Listener
  {
    public abstract void markLost();
  }
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.hw.RendererDelegate
 * JD-Core Version:    0.6.2
 */