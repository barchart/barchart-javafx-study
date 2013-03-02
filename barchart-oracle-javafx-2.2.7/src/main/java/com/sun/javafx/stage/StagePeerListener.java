/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import javafx.stage.Stage;
/*    */ 
/*    */ public class StagePeerListener extends WindowPeerListener
/*    */ {
/*    */   private final Stage stage;
/*    */   private final StageAccessor stageAccessor;
/*    */ 
/*    */   public StagePeerListener(Stage paramStage, StageAccessor paramStageAccessor)
/*    */   {
/* 42 */     super(paramStage);
/* 43 */     this.stage = paramStage;
/* 44 */     this.stageAccessor = paramStageAccessor;
/*    */   }
/*    */ 
/*    */   public void changedIconified(boolean paramBoolean)
/*    */   {
/* 50 */     this.stageAccessor.setIconified(this.stage, paramBoolean);
/*    */   }
/*    */ 
/*    */   public void changedResizable(boolean paramBoolean)
/*    */   {
/* 55 */     this.stageAccessor.setResizable(this.stage, paramBoolean);
/*    */   }
/*    */ 
/*    */   public void changedFullscreen(boolean paramBoolean)
/*    */   {
/* 60 */     this.stageAccessor.setFullScreen(this.stage, paramBoolean);
/*    */   }
/*    */ 
/*    */   public static abstract interface StageAccessor
/*    */   {
/*    */     public abstract void setIconified(Stage paramStage, boolean paramBoolean);
/*    */ 
/*    */     public abstract void setResizable(Stage paramStage, boolean paramBoolean);
/*    */ 
/*    */     public abstract void setFullScreen(Stage paramStage, boolean paramBoolean);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.StagePeerListener
 * JD-Core Version:    0.6.2
 */