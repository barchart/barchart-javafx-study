/*    */ package com.sun.prism.image;
/*    */ 
/*    */ import com.sun.prism.Image;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.Texture;
/*    */ 
/*    */ public class CachingCompoundImage extends CompoundImage
/*    */ {
/*    */   public CachingCompoundImage(Image paramImage, int paramInt)
/*    */   {
/* 22 */     super(paramImage, paramInt);
/*    */   }
/*    */ 
/*    */   public Texture getTile(int paramInt1, int paramInt2, ResourceFactory paramResourceFactory)
/*    */   {
/* 27 */     return paramResourceFactory.getCachedTexture(this.tiles[(paramInt1 + paramInt2 * this.uSections)], false);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.image.CachingCompoundImage
 * JD-Core Version:    0.6.2
 */