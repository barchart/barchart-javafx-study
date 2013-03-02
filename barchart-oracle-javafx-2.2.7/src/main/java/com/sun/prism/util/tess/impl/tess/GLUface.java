package com.sun.prism.util.tess.impl.tess;

class GLUface
{
  public GLUface next;
  public GLUface prev;
  public GLUhalfEdge anEdge;
  public Object data;
  public GLUface trail;
  public boolean marked;
  public boolean inside;
}

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.GLUface
 * JD-Core Version:    0.6.2
 */