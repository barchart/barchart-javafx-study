/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public final class BorderStyle
/*    */ {
/* 15 */   public static final BorderStyle SOLID = new BorderStyle(PGShape.StrokeType.CENTERED, PGShape.StrokeLineCap.BUTT, PGShape.StrokeLineJoin.MITER, 10.0F, new float[0], 0.0F);
/*    */   public final PGShape.StrokeType strokeType;
/*    */   public final PGShape.StrokeLineCap lineCap;
/*    */   public final PGShape.StrokeLineJoin lineJoin;
/*    */   public final float strokeMiterLimit;
/*    */   public final float[] strokeDashArray;
/*    */   public final float strokeDashOffset;
/*    */ 
/*    */   public BorderStyle(PGShape.StrokeType paramStrokeType, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat1, float[] paramArrayOfFloat, float paramFloat2)
/*    */   {
/* 34 */     this.strokeType = paramStrokeType;
/* 35 */     this.lineCap = paramStrokeLineCap;
/* 36 */     this.lineJoin = paramStrokeLineJoin;
/* 37 */     this.strokeMiterLimit = paramFloat1;
/* 38 */     this.strokeDashArray = paramArrayOfFloat;
/* 39 */     this.strokeDashOffset = paramFloat2;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 43 */     return "BorderStyle{strokeType=" + this.strokeType + ", lineCap=" + this.lineCap + ", lineJoin=" + this.lineJoin + ", strokeMiterLimit=" + this.strokeMiterLimit + ", strokeDashArray=" + Arrays.toString(this.strokeDashArray) + ", strokeDashOffset=" + this.strokeDashOffset + "} " + SOLID.equals(this);
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 55 */     if (this == paramObject) return true;
/* 56 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) return false;
/*    */ 
/* 58 */     BorderStyle localBorderStyle = (BorderStyle)paramObject;
/*    */ 
/* 60 */     if (Float.compare(localBorderStyle.strokeDashOffset, this.strokeDashOffset) != 0) return false;
/* 61 */     if (Float.compare(localBorderStyle.strokeMiterLimit, this.strokeMiterLimit) != 0) return false;
/* 62 */     if (this.lineCap != localBorderStyle.lineCap) return false;
/* 63 */     if (this.lineJoin != localBorderStyle.lineJoin) return false;
/* 64 */     if (!Arrays.equals(this.strokeDashArray, localBorderStyle.strokeDashArray)) return false;
/* 65 */     if (this.strokeType != localBorderStyle.strokeType) return false;
/*    */ 
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 72 */     int i = this.strokeType != null ? this.strokeType.hashCode() : 0;
/* 73 */     i = 31 * i + (this.lineCap != null ? this.lineCap.hashCode() : 0);
/* 74 */     i = 31 * i + (this.lineJoin != null ? this.lineJoin.hashCode() : 0);
/* 75 */     i = 31 * i + (this.strokeMiterLimit != 0.0F ? Float.floatToIntBits(this.strokeMiterLimit) : 0);
/* 76 */     i = 31 * i + (this.strokeDashArray != null ? Arrays.hashCode(this.strokeDashArray) : 0);
/* 77 */     i = 31 * i + (this.strokeDashOffset != 0.0F ? Float.floatToIntBits(this.strokeDashOffset) : 0);
/* 78 */     return i;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.BorderStyle
 * JD-Core Version:    0.6.2
 */