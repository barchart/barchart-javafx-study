/*     */ package com.sun.javafx.robot.impl;
/*     */ 
/*     */ import com.sun.javafx.robot.FXRobotImage;
/*     */ import java.io.PrintStream;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ import javafx.scene.input.ScrollEvent.HorizontalTextScrollUnits;
/*     */ import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public class FXRobotHelper
/*     */ {
/*     */   static FXRobotInputAccessor inputAccessor;
/*     */   static FXRobotSceneAccessor sceneAccessor;
/*     */   static FXRobotStageAccessor stageAccessor;
/*     */   static FXRobotImageConvertor imageConvertor;
/*     */ 
/*     */   public static ObservableList<Node> getChildren(Parent paramParent)
/*     */   {
/*  83 */     if (sceneAccessor == null);
/*  86 */     return sceneAccessor.getChildren(paramParent);
/*     */   }
/*     */ 
/*     */   public static ObservableList<Stage> getStages()
/*     */   {
/*  98 */     if (stageAccessor == null);
/* 101 */     return stageAccessor.getStages();
/*     */   }
/*     */ 
/*     */   public static Color argbToColor(int paramInt)
/*     */   {
/* 109 */     int i = paramInt >> 24;
/* 110 */     i &= 255;
/* 111 */     float f = i / 255.0F;
/*     */ 
/* 113 */     int j = paramInt >> 16;
/* 114 */     j &= 255;
/*     */ 
/* 116 */     int k = paramInt >> 8;
/* 117 */     k &= 255;
/*     */ 
/* 119 */     int m = paramInt;
/* 120 */     m &= 255;
/*     */ 
/* 122 */     return Color.rgb(j, k, m, f);
/*     */   }
/*     */ 
/*     */   public static void setInputAccessor(FXRobotInputAccessor paramFXRobotInputAccessor)
/*     */   {
/* 129 */     if (inputAccessor != null) {
/* 130 */       System.out.println("Warning: Input accessor is already set: " + inputAccessor);
/* 131 */       Thread.dumpStack();
/*     */     }
/* 133 */     inputAccessor = paramFXRobotInputAccessor;
/*     */   }
/*     */ 
/*     */   public static void setSceneAccessor(FXRobotSceneAccessor paramFXRobotSceneAccessor)
/*     */   {
/* 140 */     if (sceneAccessor != null) {
/* 141 */       System.out.println("Warning: Scene accessor is already set: " + sceneAccessor);
/* 142 */       Thread.dumpStack();
/*     */     }
/* 144 */     sceneAccessor = paramFXRobotSceneAccessor;
/*     */   }
/*     */ 
/*     */   public static void setImageConvertor(FXRobotImageConvertor paramFXRobotImageConvertor)
/*     */   {
/* 151 */     if (imageConvertor != null) {
/* 152 */       System.out.println("Warning: Image convertor is already set: " + imageConvertor);
/* 153 */       Thread.dumpStack();
/*     */     }
/* 155 */     imageConvertor = paramFXRobotImageConvertor;
/*     */   }
/*     */ 
/*     */   public static void setStageAccessor(FXRobotStageAccessor paramFXRobotStageAccessor)
/*     */   {
/* 162 */     if (stageAccessor != null) {
/* 163 */       System.out.println("Warning: Stage accessor already set: " + stageAccessor);
/* 164 */       Thread.dumpStack();
/*     */     }
/* 166 */     stageAccessor = paramFXRobotStageAccessor;
/*     */   }
/*     */ 
/*     */   public static abstract class FXRobotSceneAccessor
/*     */   {
/*     */     public abstract void processKeyEvent(Scene paramScene, KeyEvent paramKeyEvent);
/*     */ 
/*     */     public abstract void processMouseEvent(Scene paramScene, MouseEvent paramMouseEvent);
/*     */ 
/*     */     public abstract void processScrollEvent(Scene paramScene, ScrollEvent paramScrollEvent);
/*     */ 
/*     */     public abstract ObservableList<Node> getChildren(Parent paramParent);
/*     */ 
/*     */     public abstract Object renderToImage(Scene paramScene, Object paramObject);
/*     */   }
/*     */ 
/*     */   public static abstract class FXRobotInputAccessor
/*     */   {
/*     */     public abstract int getCodeForKeyCode(KeyCode paramKeyCode);
/*     */ 
/*     */     public abstract KeyCode getKeyCodeForCode(int paramInt);
/*     */ 
/*     */     public abstract KeyEvent createKeyEvent(EventType<? extends KeyEvent> paramEventType, KeyCode paramKeyCode, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
/*     */ 
/*     */     public abstract MouseEvent createMouseEvent(EventType<? extends MouseEvent> paramEventType, int paramInt1, int paramInt2, int paramInt3, int paramInt4, MouseButton paramMouseButton, int paramInt5, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8);
/*     */ 
/*     */     public abstract ScrollEvent createScrollEvent(EventType<? extends ScrollEvent> paramEventType, int paramInt1, int paramInt2, ScrollEvent.HorizontalTextScrollUnits paramHorizontalTextScrollUnits, int paramInt3, ScrollEvent.VerticalTextScrollUnits paramVerticalTextScrollUnits, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
/*     */   }
/*     */ 
/*     */   public static abstract class FXRobotImageConvertor
/*     */   {
/*     */     public abstract FXRobotImage convertToFXRobotImage(Object paramObject);
/*     */   }
/*     */ 
/*     */   public static abstract class FXRobotStageAccessor
/*     */   {
/*     */     public abstract ObservableList<Stage> getStages();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.robot.impl.FXRobotHelper
 * JD-Core Version:    0.6.2
 */