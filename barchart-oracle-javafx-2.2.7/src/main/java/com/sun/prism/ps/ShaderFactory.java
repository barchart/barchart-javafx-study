package com.sun.prism.ps;

import com.sun.prism.ResourceFactory;
import java.io.InputStream;
import java.util.Map;

public abstract interface ShaderFactory extends ResourceFactory
{
  public abstract Shader createShader(InputStream paramInputStream, Map<String, Integer> paramMap1, Map<String, Integer> paramMap2, int paramInt, boolean paramBoolean1, boolean paramBoolean2);

  public abstract Shader createStockShader(String paramString);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.ps.ShaderFactory
 * JD-Core Version:    0.6.2
 */