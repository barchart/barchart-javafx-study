/*     */ package com.sun.prism.util.tess;
/*     */ 
/*     */ import com.sun.prism.util.tess.impl.tess.TessellatorImpl;
/*     */ 
/*     */ public class Tess
/*     */ {
/*     */   public static final int GL_POINTS = 0;
/*     */   public static final int GL_LINES = 1;
/*     */   public static final int GL_LINE_LOOP = 2;
/*     */   public static final int GL_LINE_STRIP = 3;
/*     */   public static final int GL_TRIANGLES = 4;
/*     */   public static final int GL_TRIANGLE_STRIP = 5;
/*     */   public static final int GL_TRIANGLE_FAN = 6;
/*     */   public static final int INVALID_ENUM = 100900;
/*     */   public static final int INVALID_VALUE = 100901;
/*     */   public static final int OUT_OF_MEMORY = 100902;
/*     */   public static final int INVALID_OPERATION = 100904;
/*     */   public static final int TESS_BEGIN = 100100;
/*     */   public static final int BEGIN = 100100;
/*     */   public static final int TESS_VERTEX = 100101;
/*     */   public static final int VERTEX = 100101;
/*     */   public static final int TESS_END = 100102;
/*     */   public static final int END = 100102;
/*     */   public static final int TESS_ERROR = 100103;
/*     */   public static final int TESS_EDGE_FLAG = 100104;
/*     */   public static final int EDGE_FLAG = 100104;
/*     */   public static final int TESS_COMBINE = 100105;
/*     */   public static final int TESS_BEGIN_DATA = 100106;
/*     */   public static final int TESS_VERTEX_DATA = 100107;
/*     */   public static final int TESS_END_DATA = 100108;
/*     */   public static final int TESS_ERROR_DATA = 100109;
/*     */   public static final int TESS_EDGE_FLAG_DATA = 100110;
/*     */   public static final int TESS_COMBINE_DATA = 100111;
/*     */   public static final int TESS_WINDING_RULE = 100140;
/*     */   public static final int TESS_BOUNDARY_ONLY = 100141;
/*     */   public static final int TESS_TOLERANCE = 100142;
/*     */   public static final int TESS_AVOID_DEGENERATE_TRIANGLES = 100149;
/*     */   public static final int TESS_ERROR1 = 100151;
/*     */   public static final int TESS_ERROR2 = 100152;
/*     */   public static final int TESS_ERROR3 = 100153;
/*     */   public static final int TESS_ERROR4 = 100154;
/*     */   public static final int TESS_ERROR5 = 100155;
/*     */   public static final int TESS_ERROR6 = 100156;
/*     */   public static final int TESS_MISSING_BEGIN_POLYGON = 100151;
/*     */   public static final int TESS_MISSING_BEGIN_CONTOUR = 100152;
/*     */   public static final int TESS_MISSING_END_POLYGON = 100153;
/*     */   public static final int TESS_MISSING_END_CONTOUR = 100154;
/*     */   public static final int TESS_COORD_TOO_LARGE = 100155;
/*     */   public static final int TESS_NEED_COMBINE_CALLBACK = 100156;
/*     */   public static final int TESS_WINDING_ODD = 100130;
/*     */   public static final int TESS_WINDING_NONZERO = 100131;
/*     */   public static final int TESS_WINDING_POSITIVE = 100132;
/*     */   public static final int TESS_WINDING_NEGATIVE = 100133;
/*     */   public static final int TESS_WINDING_ABS_GEQ_TWO = 100134;
/*     */   public static final double TESS_MAX_COORD = 1.0E+150D;
/*     */ 
/*     */   public static final Tessellator newTess()
/*     */   {
/*  51 */     return TessellatorImpl.gluNewTess();
/*     */   }
/*     */ 
/*     */   public static final void deleteTess(Tessellator paramTessellator)
/*     */   {
/*  68 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/*  69 */     localTessellatorImpl.gluDeleteTess();
/*     */   }
/*     */ 
/*     */   public static final void tessProperty(Tessellator paramTessellator, int paramInt, double paramDouble)
/*     */   {
/* 152 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 153 */     localTessellatorImpl.gluTessProperty(paramInt, paramDouble);
/*     */   }
/*     */ 
/*     */   public static final void gluGetTessProperty(Tessellator paramTessellator, int paramInt1, double[] paramArrayOfDouble, int paramInt2)
/*     */   {
/* 180 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 181 */     localTessellatorImpl.gluGetTessProperty(paramInt1, paramArrayOfDouble, paramInt2);
/*     */   }
/*     */ 
/*     */   public static final void tessNormal(Tessellator paramTessellator, double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 221 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 222 */     localTessellatorImpl.gluTessNormal(paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */   public static final void tessCallback(Tessellator paramTessellator, int paramInt, TessellatorCallback paramTessellatorCallback)
/*     */   {
/* 506 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 507 */     localTessellatorImpl.gluTessCallback(paramInt, paramTessellatorCallback);
/*     */   }
/*     */ 
/*     */   public static final void tessVertex(Tessellator paramTessellator, double[] paramArrayOfDouble, int paramInt, Object paramObject)
/*     */   {
/* 546 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 547 */     localTessellatorImpl.gluTessVertex(paramArrayOfDouble, paramInt, paramObject);
/*     */   }
/*     */ 
/*     */   public static final void tessBeginPolygon(Tessellator paramTessellator, Object paramObject)
/*     */   {
/* 596 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 597 */     localTessellatorImpl.gluTessBeginPolygon(paramObject);
/*     */   }
/*     */ 
/*     */   public static final void tessBeginContour(Tessellator paramTessellator)
/*     */   {
/* 630 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 631 */     localTessellatorImpl.gluTessBeginContour();
/*     */   }
/*     */ 
/*     */   public static final void tessEndContour(Tessellator paramTessellator)
/*     */   {
/* 664 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 665 */     localTessellatorImpl.gluTessEndContour();
/*     */   }
/*     */ 
/*     */   public static final void tessEndPolygon(Tessellator paramTessellator)
/*     */   {
/* 704 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 705 */     localTessellatorImpl.gluTessEndPolygon();
/*     */   }
/*     */ 
/*     */   public static final void beginPolygon(Tessellator paramTessellator)
/*     */   {
/* 740 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 741 */     localTessellatorImpl.gluBeginPolygon();
/*     */   }
/*     */ 
/*     */   public static final void nextContour(Tessellator paramTessellator, int paramInt)
/*     */   {
/* 813 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 814 */     localTessellatorImpl.gluNextContour(paramInt);
/*     */   }
/*     */ 
/*     */   public static final void endPolygon(Tessellator paramTessellator)
/*     */   {
/* 848 */     TessellatorImpl localTessellatorImpl = (TessellatorImpl)paramTessellator;
/* 849 */     localTessellatorImpl.gluEndPolygon();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.Tess
 * JD-Core Version:    0.6.2
 */