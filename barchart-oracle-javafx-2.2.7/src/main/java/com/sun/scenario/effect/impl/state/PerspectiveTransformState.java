/*    */ package com.sun.scenario.effect.impl.state;
/*    */ 
/*    */ public class PerspectiveTransformState
/*    */ {
/* 28 */   private float[][] itx = new float[3][3];
/*    */ 
/*    */   public float[][] getITX() {
/* 31 */     return this.itx;
/*    */   }
/*    */ 
/*    */   public void updateTx(float[][] paramArrayOfFloat)
/*    */   {
/* 36 */     float f1 = get3x3Determinant(paramArrayOfFloat);
/* 37 */     if (Math.abs(f1) < 1.0E-10D)
/*    */     {
/*    */       float tmp41_40 = (this.itx[2][0] = 0.0F); this.itx[1][0] = tmp41_40; this.itx[0][0] = tmp41_40;
/*    */       float tmp68_67 = (this.itx[2][1] = 0.0F); this.itx[1][1] = tmp68_67; this.itx[0][1] = tmp68_67;
/*    */       float tmp87_85 = -1.0F; this.itx[1][2] = tmp87_85; this.itx[0][2] = tmp87_85;
/* 41 */       this.itx[2][2] = 1.0F;
/*    */     } else {
/* 43 */       float f2 = 1.0F / f1;
/*    */ 
/* 48 */       this.itx[0][0] = (f2 * (paramArrayOfFloat[1][1] * paramArrayOfFloat[2][2] - paramArrayOfFloat[1][2] * paramArrayOfFloat[2][1]));
/* 49 */       this.itx[1][0] = (f2 * (paramArrayOfFloat[1][2] * paramArrayOfFloat[2][0] - paramArrayOfFloat[1][0] * paramArrayOfFloat[2][2]));
/* 50 */       this.itx[2][0] = (f2 * (paramArrayOfFloat[1][0] * paramArrayOfFloat[2][1] - paramArrayOfFloat[1][1] * paramArrayOfFloat[2][0]));
/* 51 */       this.itx[0][1] = (f2 * (paramArrayOfFloat[0][2] * paramArrayOfFloat[2][1] - paramArrayOfFloat[0][1] * paramArrayOfFloat[2][2]));
/* 52 */       this.itx[1][1] = (f2 * (paramArrayOfFloat[0][0] * paramArrayOfFloat[2][2] - paramArrayOfFloat[0][2] * paramArrayOfFloat[2][0]));
/* 53 */       this.itx[2][1] = (f2 * (paramArrayOfFloat[0][1] * paramArrayOfFloat[2][0] - paramArrayOfFloat[0][0] * paramArrayOfFloat[2][1]));
/* 54 */       this.itx[0][2] = (f2 * (paramArrayOfFloat[0][1] * paramArrayOfFloat[1][2] - paramArrayOfFloat[0][2] * paramArrayOfFloat[1][1]));
/* 55 */       this.itx[1][2] = (f2 * (paramArrayOfFloat[0][2] * paramArrayOfFloat[1][0] - paramArrayOfFloat[0][0] * paramArrayOfFloat[1][2]));
/* 56 */       this.itx[2][2] = (f2 * (paramArrayOfFloat[0][0] * paramArrayOfFloat[1][1] - paramArrayOfFloat[0][1] * paramArrayOfFloat[1][0]));
/*    */     }
/*    */   }
/*    */ 
/*    */   private static float get3x3Determinant(float[][] paramArrayOfFloat) {
/* 61 */     return paramArrayOfFloat[0][0] * (paramArrayOfFloat[1][1] * paramArrayOfFloat[2][2] - paramArrayOfFloat[1][2] * paramArrayOfFloat[2][1]) - paramArrayOfFloat[0][1] * (paramArrayOfFloat[1][0] * paramArrayOfFloat[2][2] - paramArrayOfFloat[1][2] * paramArrayOfFloat[2][0]) + paramArrayOfFloat[0][2] * (paramArrayOfFloat[1][0] * paramArrayOfFloat[2][1] - paramArrayOfFloat[1][1] * paramArrayOfFloat[2][0]);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.PerspectiveTransformState
 * JD-Core Version:    0.6.2
 */