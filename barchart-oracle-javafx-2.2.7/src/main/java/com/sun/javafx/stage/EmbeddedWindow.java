/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import com.sun.javafx.embed.HostInterface;
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.stage.Window;
/*    */ 
/*    */ public class EmbeddedWindow extends Window
/*    */ {
/*    */   private HostInterface host;
/*    */ 
/*    */   public EmbeddedWindow(HostInterface paramHostInterface)
/*    */   {
/* 43 */     this.host = paramHostInterface;
/*    */   }
/*    */ 
/*    */   public final void setScene(Scene paramScene)
/*    */   {
/* 50 */     super.setScene(paramScene);
/*    */   }
/*    */ 
/*    */   public final void show()
/*    */   {
/* 57 */     super.show();
/*    */   }
/*    */ 
/*    */   protected void impl_visibleChanging(boolean paramBoolean)
/*    */   {
/* 62 */     super.impl_visibleChanging(paramBoolean);
/* 63 */     Toolkit localToolkit = Toolkit.getToolkit();
/* 64 */     if ((paramBoolean) && (this.impl_peer == null))
/*    */     {
/* 66 */       this.impl_peer = localToolkit.createTKEmbeddedStage(this.host);
/* 67 */       this.peerListener = new WindowPeerListener(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void impl_visibleChanged(boolean paramBoolean)
/*    */   {
/* 73 */     super.impl_visibleChanged(paramBoolean);
/* 74 */     if ((!paramBoolean) && (this.impl_peer != null)) {
/* 75 */       this.peerListener = null;
/* 76 */       this.impl_peer = null;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.EmbeddedWindow
 * JD-Core Version:    0.6.2
 */