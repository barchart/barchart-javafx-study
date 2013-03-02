/*    */ package com.sun.prism.impl.shape;
/*    */ 
/*    */ import com.sun.prism.PixelFormat;
/*    */ import com.sun.prism.Texture;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class MaskData
/*    */ {
/*    */   private ByteBuffer maskBuffer;
/*    */   private int originX;
/*    */   private int originY;
/*    */   private int width;
/*    */   private int height;
/*    */ 
/*    */   public ByteBuffer getMaskBuffer()
/*    */   {
/* 22 */     return this.maskBuffer;
/*    */   }
/*    */ 
/*    */   public int getOriginX() {
/* 26 */     return this.originX;
/*    */   }
/*    */ 
/*    */   public int getOriginY() {
/* 30 */     return this.originY;
/*    */   }
/*    */ 
/*    */   public int getWidth() {
/* 34 */     return this.width;
/*    */   }
/*    */ 
/*    */   public int getHeight() {
/* 38 */     return this.height;
/*    */   }
/*    */ 
/*    */   public void uploadToTexture(Texture paramTexture, int paramInt1, int paramInt2, boolean paramBoolean)
/*    */   {
/* 44 */     int i = this.width * paramTexture.getPixelFormat().getBytesPerPixelUnit();
/* 45 */     paramTexture.update(this.maskBuffer, paramTexture.getPixelFormat(), paramInt1, paramInt2, 0, 0, this.width, this.height, i, paramBoolean);
/*    */   }
/*    */ 
/*    */   public void update(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 53 */     this.maskBuffer = paramByteBuffer;
/* 54 */     this.originX = paramInt1;
/* 55 */     this.originY = paramInt2;
/* 56 */     this.width = paramInt3;
/* 57 */     this.height = paramInt4;
/*    */   }
/*    */ 
/*    */   public static MaskData create(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 64 */     MaskData localMaskData = new MaskData();
/* 65 */     localMaskData.update(ByteBuffer.wrap(paramArrayOfByte), paramInt1, paramInt2, paramInt3, paramInt4);
/* 66 */     return localMaskData;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.MaskData
 * JD-Core Version:    0.6.2
 */