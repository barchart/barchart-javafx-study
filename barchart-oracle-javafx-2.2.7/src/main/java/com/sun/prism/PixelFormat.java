/*    */ package com.sun.prism;
/*    */ 
/*    */ public enum PixelFormat
/*    */ {
/* 14 */   INT_ARGB_PRE(DataType.INT, 1, true, false), 
/*    */ 
/* 17 */   BYTE_BGRA_PRE(DataType.BYTE, 4, true, false), 
/*    */ 
/* 20 */   BYTE_RGB(DataType.BYTE, 3, true, true), 
/*    */ 
/* 24 */   BYTE_GRAY(DataType.BYTE, 1, true, true), 
/* 25 */   BYTE_ALPHA(DataType.BYTE, 1, false, false), 
/*    */ 
/* 28 */   MULTI_YCbCr_420(DataType.BYTE, 1, false, true), 
/* 29 */   BYTE_APPLE_422(DataType.BYTE, 2, false, true), 
/*    */ 
/* 32 */   FLOAT_XYZW(DataType.FLOAT, 4, false, true);
/*    */ 
/*    */   public static final int YCBCR_PLANE_LUMA = 0;
/*    */   public static final int YCBCR_PLANE_CHROMARED = 1;
/*    */   public static final int YCBCR_PLANE_CHROMABLUE = 2;
/*    */   public static final int YCBCR_PLANE_ALPHA = 3;
/*    */   private DataType dataType;
/*    */   private int elemsPerPixelUnit;
/*    */   private boolean rgb;
/*    */   private boolean opaque;
/*    */ 
/*    */   private PixelFormat(DataType paramDataType, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*    */   {
/* 68 */     this.dataType = paramDataType;
/* 69 */     this.elemsPerPixelUnit = paramInt;
/* 70 */     this.rgb = paramBoolean1;
/* 71 */     this.opaque = paramBoolean2;
/*    */   }
/*    */ 
/*    */   public DataType getDataType() {
/* 75 */     return this.dataType;
/*    */   }
/*    */ 
/*    */   public int getBytesPerPixelUnit() {
/* 79 */     return this.elemsPerPixelUnit * this.dataType.getSizeInBytes();
/*    */   }
/*    */ 
/*    */   public int getElemsPerPixelUnit() {
/* 83 */     return this.elemsPerPixelUnit;
/*    */   }
/*    */ 
/*    */   public boolean isRGB() {
/* 87 */     return this.rgb;
/*    */   }
/*    */ 
/*    */   public boolean isOpaque() {
/* 91 */     return this.opaque;
/*    */   }
/*    */ 
/*    */   public static enum DataType
/*    */   {
/* 39 */     BYTE(1), 
/* 40 */     INT(4), 
/* 41 */     FLOAT(4);
/*    */ 
/*    */     private int sizeInBytes;
/*    */ 
/*    */     private DataType(int paramInt) {
/* 46 */       this.sizeInBytes = paramInt;
/*    */     }
/*    */ 
/*    */     public int getSizeInBytes() {
/* 50 */       return this.sizeInBytes;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.PixelFormat
 * JD-Core Version:    0.6.2
 */