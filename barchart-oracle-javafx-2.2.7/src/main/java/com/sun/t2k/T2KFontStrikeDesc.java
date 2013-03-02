/*    */ package com.sun.t2k;
/*    */ 
/*    */ import com.sun.javafx.font.FontStrikeDesc;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ 
/*    */ public class T2KFontStrikeDesc
/*    */   implements FontStrikeDesc
/*    */ {
/*    */   float[] matrix;
/*    */   float size;
/*    */   int aaMode;
/*    */   private int hash;
/*    */ 
/*    */   public T2KFontStrikeDesc(float paramFloat, BaseTransform paramBaseTransform, int paramInt)
/*    */   {
/* 26 */     BaseTransform localBaseTransform = paramBaseTransform;
/* 27 */     this.size = paramFloat;
/* 28 */     this.aaMode = paramInt;
/* 29 */     this.matrix = new float[4];
/* 30 */     this.matrix[0] = ((float)localBaseTransform.getMxx());
/* 31 */     this.matrix[1] = ((float)localBaseTransform.getMxy());
/* 32 */     this.matrix[2] = ((float)localBaseTransform.getMyx());
/* 33 */     this.matrix[3] = ((float)localBaseTransform.getMyy());
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 39 */     if (this.hash == 0) {
/* 40 */       this.hash = (this.aaMode + Float.floatToIntBits(this.size) + Float.floatToIntBits(this.matrix[0]) + Float.floatToIntBits(this.matrix[1]) + Float.floatToIntBits(this.matrix[2]) + Float.floatToIntBits(this.matrix[3]));
/*    */     }
/*    */ 
/* 48 */     return this.hash;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 60 */     T2KFontStrikeDesc localT2KFontStrikeDesc = (T2KFontStrikeDesc)paramObject;
/* 61 */     return (this.aaMode == localT2KFontStrikeDesc.aaMode) && (this.matrix[0] == localT2KFontStrikeDesc.matrix[0]) && (this.matrix[1] == localT2KFontStrikeDesc.matrix[1]) && (this.matrix[2] == localT2KFontStrikeDesc.matrix[2]) && (this.matrix[3] == localT2KFontStrikeDesc.matrix[3]) && (this.size == localT2KFontStrikeDesc.size);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KFontStrikeDesc
 * JD-Core Version:    0.6.2
 */