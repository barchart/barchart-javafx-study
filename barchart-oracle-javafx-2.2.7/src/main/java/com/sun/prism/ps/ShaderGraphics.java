package com.sun.prism.ps;

import com.sun.javafx.geom.transform.Affine3D;
import com.sun.prism.Graphics;
import com.sun.prism.Texture;

public abstract interface ShaderGraphics extends Graphics
{
  public abstract void getPaintShaderTransform(Affine3D paramAffine3D);

  public abstract void setExternalShader(Shader paramShader);

  public abstract void drawTextureRaw2(Texture paramTexture1, Texture paramTexture2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12);

  public abstract void drawMappedTextureRaw2(Texture paramTexture1, Texture paramTexture2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16, float paramFloat17, float paramFloat18, float paramFloat19, float paramFloat20);
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.ps.ShaderGraphics
 * JD-Core Version:    0.6.2
 */