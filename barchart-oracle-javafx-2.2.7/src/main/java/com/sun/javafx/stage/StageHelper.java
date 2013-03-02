/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.stage.Stage;
/*    */ 
/*    */ public class StageHelper
/*    */ {
/*    */   private static StageAccessor stageAccessor;
/*    */ 
/*    */   public static ObservableList<Stage> getStages()
/*    */   {
/* 52 */     if (stageAccessor == null)
/*    */       try
/*    */       {
/* 55 */         Class.forName(Stage.class.getName(), true, Stage.class.getClassLoader());
/*    */       }
/*    */       catch (ClassNotFoundException localClassNotFoundException)
/*    */       {
/*    */       }
/* 60 */     return stageAccessor.getStages();
/*    */   }
/*    */ 
/*    */   public static void setStageAccessor(StageAccessor paramStageAccessor) {
/* 64 */     if (stageAccessor != null) {
/* 65 */       System.out.println("Warning: Stage accessor already set: " + stageAccessor);
/* 66 */       Thread.dumpStack();
/*    */     }
/* 68 */     stageAccessor = paramStageAccessor;
/*    */   }
/*    */ 
/*    */   public static abstract interface StageAccessor
/*    */   {
/*    */     public abstract ObservableList<Stage> getStages();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.StageHelper
 * JD-Core Version:    0.6.2
 */