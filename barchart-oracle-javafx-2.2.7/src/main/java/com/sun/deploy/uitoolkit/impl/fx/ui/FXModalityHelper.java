/*    */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*    */ 
/*    */ import com.sun.deploy.uitoolkit.ui.AbstractDialog;
/*    */ import com.sun.deploy.uitoolkit.ui.ModalityHelper;
/*    */ import java.io.PrintStream;
/*    */ import sun.plugin2.applet.Plugin2Manager;
/*    */ import sun.plugin2.main.client.ModalityInterface;
/*    */ 
/*    */ public class FXModalityHelper
/*    */   implements ModalityHelper
/*    */ {
/*    */   public void reactivateDialog(AbstractDialog ad)
/*    */   {
/* 16 */     System.out.println("FXModalityHelper.reactivateDialog");
/*    */   }
/*    */ 
/*    */   public boolean installModalityListener(ModalityInterface mi) {
/* 20 */     System.out.println("FXModalityHelper.installModalityListener" + mi);
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   public void pushManagerShowingSystemDialog() {
/* 25 */     System.out.println("FXModalityHelper.pushManagerShowingSystemDialog");
/*    */   }
/*    */ 
/*    */   public Plugin2Manager getManagerShowingSystemDialog() {
/* 29 */     System.out.println("FXModalityHelper.getManagerShowingSystemDialog");
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   public void popManagerShowingSystemDialog() {
/* 34 */     System.out.println("FXModalityHelper.popManagerShowingSystemDialog");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXModalityHelper
 * JD-Core Version:    0.6.2
 */