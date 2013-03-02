/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.glass.ui.ClipboardAssistance;
/*    */ import com.sun.javafx.tk.TKDropEvent;
/*    */ import javafx.scene.input.Dragboard;
/*    */ import javafx.scene.input.TransferMode;
/*    */ 
/*    */ public class GlassDragEvent extends TKDropEvent
/*    */ {
/*    */   public static final int START = 1;
/*    */   public static final int END = 2;
/*    */   public static final int ENTER = 3;
/*    */   public static final int OVER = 4;
/*    */   public static final int LEAVE = 5;
/*    */   private Dragboard dragboard;
/*    */   private int x;
/*    */   private int y;
/*    */   private int xAbs;
/*    */   private int yAbs;
/*    */   int button;
/*    */   GlassScene glassScene;
/*    */   int type;
/*    */   int recommendedDropAction;
/*    */ 
/*    */   int getType()
/*    */   {
/* 38 */     return this.type;
/*    */   }
/*    */   int getX() {
/* 41 */     return this.x;
/*    */   }
/*    */   int getY() {
/* 44 */     return this.y;
/*    */   }
/*    */   int getXAbs() {
/* 47 */     return this.xAbs;
/*    */   }
/*    */   int getYAbs() {
/* 50 */     return this.yAbs;
/*    */   }
/*    */   GlassScene getGlassScene() {
/* 53 */     return this.glassScene;
/*    */   }
/*    */   public int getRecommendedDropAction() {
/* 56 */     return this.recommendedDropAction;
/*    */   }
/*    */ 
/*    */   GlassDragEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Dragboard paramDragboard, int paramInt6, GlassScene paramGlassScene, int paramInt7)
/*    */   {
/* 62 */     this.type = paramInt1;
/*    */ 
/* 71 */     this.dragboard = paramDragboard;
/*    */ 
/* 73 */     this.x = paramInt2;
/* 74 */     this.y = paramInt3;
/* 75 */     this.xAbs = paramInt4;
/* 76 */     this.yAbs = paramInt5;
/* 77 */     this.button = paramInt6;
/* 78 */     this.glassScene = paramGlassScene;
/* 79 */     this.recommendedDropAction = paramInt7;
/*    */   }
/*    */ 
/*    */   public void accept(TransferMode paramTransferMode)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void reject()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Dragboard getDragboard()
/*    */   {
/* 92 */     if (this.dragboard == null) {
/* 93 */       QuantumClipboard localQuantumClipboard = QuantumClipboard.getDragboardInstance(new ClipboardAssistance("DND"));
/*    */ 
/* 95 */       this.dragboard = Dragboard.impl_create(localQuantumClipboard);
/*    */     }
/* 97 */     return this.dragboard;
/*    */   }
/*    */ 
/*    */   public void dropComplete(boolean paramBoolean)
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassDragEvent
 * JD-Core Version:    0.6.2
 */