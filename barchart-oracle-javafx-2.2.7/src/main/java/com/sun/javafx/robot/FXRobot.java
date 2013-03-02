/*     */ package com.sun.javafx.robot;
/*     */ 
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.MouseButton;
/*     */ 
/*     */ public abstract class FXRobot
/*     */ {
/*  56 */   protected boolean autoWait = false;
/*     */ 
/*     */   public abstract void waitForIdle();
/*     */ 
/*     */   public void setAutoWaitForIdle(boolean paramBoolean)
/*     */   {
/*  68 */     paramBoolean = this.autoWait;
/*     */   }
/*     */ 
/*     */   public abstract void keyPress(KeyCode paramKeyCode);
/*     */ 
/*     */   public abstract void keyRelease(KeyCode paramKeyCode);
/*     */ 
/*     */   public abstract void keyType(KeyCode paramKeyCode, String paramString);
/*     */ 
/*     */   public abstract void mouseMove(int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract void mousePress(MouseButton paramMouseButton, int paramInt);
/*     */ 
/*     */   public abstract void mouseRelease(MouseButton paramMouseButton, int paramInt);
/*     */ 
/*     */   public abstract void mouseClick(MouseButton paramMouseButton, int paramInt);
/*     */ 
/*     */   public void mousePress(MouseButton paramMouseButton)
/*     */   {
/* 163 */     mousePress(paramMouseButton, 1);
/*     */   }
/*     */ 
/*     */   public void mouseRelease(MouseButton paramMouseButton)
/*     */   {
/* 175 */     mouseRelease(paramMouseButton, 1);
/*     */   }
/*     */ 
/*     */   public void mouseClick(MouseButton paramMouseButton)
/*     */   {
/* 184 */     mouseClick(paramMouseButton, 1);
/*     */   }
/*     */ 
/*     */   public abstract void mouseDrag(MouseButton paramMouseButton);
/*     */ 
/*     */   public abstract void mouseWheel(int paramInt);
/*     */ 
/*     */   public abstract int getPixelColor(int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract FXRobotImage getSceneCapture(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.robot.FXRobot
 * JD-Core Version:    0.6.2
 */