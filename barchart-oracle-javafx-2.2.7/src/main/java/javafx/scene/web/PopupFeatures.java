/*    */ package javafx.scene.web;
/*    */ 
/*    */ public class PopupFeatures
/*    */ {
/*    */   private boolean menu;
/*    */   private boolean status;
/*    */   private boolean toolbar;
/*    */   private boolean resizable;
/*    */ 
/*    */   public PopupFeatures(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*    */   {
/* 29 */     this.menu = paramBoolean1;
/* 30 */     this.status = paramBoolean2;
/* 31 */     this.toolbar = paramBoolean3;
/* 32 */     this.resizable = paramBoolean4;
/*    */   }
/*    */ 
/*    */   public boolean hasMenu()
/*    */   {
/* 39 */     return this.menu;
/*    */   }
/*    */ 
/*    */   public boolean hasStatus()
/*    */   {
/* 46 */     return this.status;
/*    */   }
/*    */ 
/*    */   public boolean hasToolbar()
/*    */   {
/* 53 */     return this.toolbar;
/*    */   }
/*    */ 
/*    */   public boolean isResizable()
/*    */   {
/* 60 */     return this.resizable;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.PopupFeatures
 * JD-Core Version:    0.6.2
 */