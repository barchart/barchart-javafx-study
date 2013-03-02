/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ public class PopupScene extends ViewScene
/*    */ {
/*    */   public PopupScene(boolean paramBoolean1, boolean paramBoolean2)
/*    */   {
/* 12 */     super(paramBoolean1, paramBoolean2);
/*    */   }
/*    */ 
/*    */   public void setGlassStage(GlassStage paramGlassStage)
/*    */   {
/* 17 */     super.setGlassStage(paramGlassStage);
/*    */   }
/*    */ 
/*    */   PopupStage getPopupStage() {
/* 21 */     return (PopupStage)this.glassStage;
/*    */   }
/*    */ 
/*    */   public void sceneChanged()
/*    */   {
/* 26 */     GlassScene localGlassScene = null;
/* 27 */     if ((getPopupStage() != null) && ((localGlassScene = getPopupStage().getOwnerScene()) != null)) {
/* 28 */       localGlassScene.sceneChanged();
/*    */     }
/* 30 */     super.sceneChanged();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PopupScene
 * JD-Core Version:    0.6.2
 */