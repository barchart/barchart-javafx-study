package com.sun.scenario.effect.impl.hw;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract interface Shader
{
  public abstract void enable();

  public abstract void disable();

  public abstract boolean isValid();

  public abstract void dispose();

  public abstract void setConstant(String paramString, int paramInt);

  public abstract void setConstant(String paramString, int paramInt1, int paramInt2);

  public abstract void setConstant(String paramString, int paramInt1, int paramInt2, int paramInt3);

  public abstract void setConstant(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void setConstants(String paramString, IntBuffer paramIntBuffer, int paramInt1, int paramInt2);

  public abstract void setConstant(String paramString, float paramFloat);

  public abstract void setConstant(String paramString, float paramFloat1, float paramFloat2);

  public abstract void setConstant(String paramString, float paramFloat1, float paramFloat2, float paramFloat3);

  public abstract void setConstant(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract void setConstants(String paramString, FloatBuffer paramFloatBuffer, int paramInt1, int paramInt2);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.hw.Shader
 * JD-Core Version:    0.6.2
 */