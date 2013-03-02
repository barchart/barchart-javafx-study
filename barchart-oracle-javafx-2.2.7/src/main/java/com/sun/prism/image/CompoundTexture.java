/*    */ package com.sun.prism.image;
/*    */ 
/*    */ import com.sun.prism.GraphicsResource;
/*    */ import com.sun.prism.Image;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.Texture;
/*    */ import com.sun.prism.Texture.Usage;
/*    */ 
/*    */ public class CompoundTexture extends CompoundImage
/*    */   implements GraphicsResource
/*    */ {
/*    */   protected Texture[] texTiles;
/*    */ 
/*    */   public CompoundTexture(Image paramImage, int paramInt)
/*    */   {
/* 17 */     super(paramImage, paramInt);
/* 18 */     this.texTiles = new Texture[this.tiles.length];
/*    */   }
/*    */ 
/*    */   public Texture getTile(int paramInt1, int paramInt2, ResourceFactory paramResourceFactory)
/*    */   {
/* 23 */     int i = paramInt1 + paramInt2 * this.uSections;
/* 24 */     Texture localTexture = this.texTiles[i];
/* 25 */     if (localTexture == null) {
/* 26 */       localTexture = paramResourceFactory.createTexture(this.tiles[i], Texture.Usage.DEFAULT, false);
/* 27 */       this.texTiles[i] = localTexture;
/*    */     }
/* 29 */     return localTexture;
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 34 */     for (int i = 0; i != this.texTiles.length; i++)
/* 35 */       if (this.texTiles[i] != null) {
/* 36 */         this.texTiles[i].dispose();
/* 37 */         this.texTiles[i] = null;
/*    */       }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.image.CompoundTexture
 * JD-Core Version:    0.6.2
 */