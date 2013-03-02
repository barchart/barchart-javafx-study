/*     */ package com.sun.javafx.robot.impl;
/*     */ 
/*     */ import com.sun.javafx.robot.FXRobot;
/*     */ import com.sun.javafx.robot.FXRobotImage;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ import javafx.scene.input.ScrollEvent.HorizontalTextScrollUnits;
/*     */ import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class BaseFXRobot extends FXRobot
/*     */ {
/*  76 */   private static final boolean debugOut = computeDebugOut();
/*     */   private static Map<KeyCode, String> keyTextMap;
/*     */   private Scene target;
/* 103 */   private boolean isShiftDown = false;
/* 104 */   private boolean isControlDown = false;
/* 105 */   private boolean isAltDown = false;
/* 106 */   private boolean isMetaDown = false;
/*     */ 
/* 108 */   private boolean isButton1Pressed = false;
/* 109 */   private boolean isButton2Pressed = false;
/* 110 */   private boolean isButton3Pressed = false;
/*     */ 
/* 112 */   private MouseButton lastButtonPressed = null;
/*     */   private double sceneMouseX;
/*     */   private double sceneMouseY;
/*     */   private double screenMouseX;
/*     */   private double screenMouseY;
/*     */   private Object lastImage;
/*     */   private FXRobotImage lastConvertedImage;
/*     */ 
/*     */   private static boolean computeDebugOut()
/*     */   {
/*  79 */     boolean bool = false;
/*     */     try {
/*  81 */       bool = "true".equals(System.getProperty("fxrobot.verbose", "false")); } catch (Throwable localThrowable) {
/*     */     }
/*  83 */     return bool;
/*     */   }
/*     */ 
/*     */   private static void out(String paramString) {
/*  87 */     if (debugOut)
/*  88 */       System.out.println("[FXRobot] " + paramString);
/*     */   }
/*     */ 
/*     */   private static String getKeyText(KeyCode paramKeyCode)
/*     */   {
/*  94 */     return paramKeyCode.getName();
/*     */   }
/*     */ 
/*     */   public BaseFXRobot(Scene paramScene)
/*     */   {
/* 100 */     this.target = paramScene;
/*     */   }
/*     */ 
/*     */   public void waitForIdle()
/*     */   {
/* 142 */     final CountDownLatch localCountDownLatch = new CountDownLatch(1);
/* 143 */     Toolkit.getToolkit().defer(new Runnable() {
/*     */       public void run() {
/* 145 */         localCountDownLatch.countDown();
/*     */       }
/*     */     });
/*     */     while (true)
/*     */       try {
/* 150 */         localCountDownLatch.await();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void keyPress(KeyCode paramKeyCode) {
/* 157 */     doKeyEvent(KeyEvent.KEY_PRESSED, paramKeyCode, "");
/*     */   }
/*     */ 
/*     */   public void keyRelease(KeyCode paramKeyCode) {
/* 161 */     doKeyEvent(KeyEvent.KEY_RELEASED, paramKeyCode, "");
/*     */   }
/*     */ 
/*     */   public void keyType(KeyCode paramKeyCode, String paramString) {
/* 165 */     doKeyEvent(KeyEvent.KEY_TYPED, paramKeyCode, paramString);
/*     */   }
/*     */ 
/*     */   public void mouseMove(int paramInt1, int paramInt2) {
/* 169 */     doMouseEvent(paramInt1, paramInt2, this.lastButtonPressed, 0, MouseEvent.MOUSE_MOVED);
/*     */   }
/*     */   public void mousePress(MouseButton paramMouseButton, int paramInt) {
/* 172 */     doMouseEvent(this.sceneMouseX, this.sceneMouseY, paramMouseButton, paramInt, MouseEvent.MOUSE_PRESSED);
/*     */   }
/*     */   public void mouseRelease(MouseButton paramMouseButton, int paramInt) {
/* 175 */     doMouseEvent(this.sceneMouseX, this.sceneMouseY, paramMouseButton, paramInt, MouseEvent.MOUSE_RELEASED);
/*     */   }
/*     */   public void mouseClick(MouseButton paramMouseButton, int paramInt) {
/* 178 */     doMouseEvent(this.sceneMouseX, this.sceneMouseY, paramMouseButton, paramInt, MouseEvent.MOUSE_CLICKED);
/*     */   }
/*     */ 
/*     */   public void mouseDrag(MouseButton paramMouseButton) {
/* 182 */     doMouseEvent(this.sceneMouseX, this.sceneMouseY, paramMouseButton, 0, MouseEvent.MOUSE_DRAGGED);
/*     */   }
/*     */   public void mouseWheel(int paramInt) {
/* 185 */     doScrollEvent(this.sceneMouseX, this.sceneMouseY, paramInt, ScrollEvent.SCROLL);
/*     */   }
/*     */ 
/*     */   public int getPixelColor(int paramInt1, int paramInt2) {
/* 189 */     FXRobotImage localFXRobotImage = getSceneCapture(0, 0, 100, 100);
/* 190 */     if (localFXRobotImage != null) {
/* 191 */       return localFXRobotImage.getArgb(paramInt1, paramInt2);
/*     */     }
/* 193 */     return 0;
/*     */   }
/*     */ 
/*     */   public FXRobotImage getSceneCapture(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 199 */     Object localObject = FXRobotHelper.sceneAccessor.renderToImage(this.target, this.lastImage);
/* 200 */     if (localObject != null) {
/* 201 */       this.lastImage = localObject;
/* 202 */       this.lastConvertedImage = FXRobotHelper.imageConvertor.convertToFXRobotImage(localObject);
/*     */     }
/*     */ 
/* 205 */     return this.lastConvertedImage;
/*     */   }
/*     */ 
/*     */   private void doKeyEvent(EventType<KeyEvent> paramEventType, KeyCode paramKeyCode, String paramString) {
/* 209 */     boolean bool = paramEventType == KeyEvent.KEY_PRESSED;
/* 210 */     int i = paramEventType == KeyEvent.KEY_TYPED ? 1 : 0;
/* 211 */     if (paramKeyCode == KeyCode.SHIFT) {
/* 212 */       this.isShiftDown = bool;
/*     */     }
/* 214 */     if (paramKeyCode == KeyCode.CONTROL) {
/* 215 */       this.isControlDown = bool;
/*     */     }
/* 217 */     if (paramKeyCode == KeyCode.ALT) {
/* 218 */       this.isAltDown = bool;
/*     */     }
/* 220 */     if (paramKeyCode == KeyCode.META) {
/* 221 */       this.isMetaDown = bool;
/*     */     }
/*     */ 
/* 224 */     String str1 = i != 0 ? "" : getKeyText(paramKeyCode);
/* 225 */     String str2 = i != 0 ? paramString : KeyEvent.CHAR_UNDEFINED;
/*     */ 
/* 227 */     final KeyEvent localKeyEvent = FXRobotHelper.inputAccessor.createKeyEvent(paramEventType, paramKeyCode, str2, str1, this.isShiftDown, this.isControlDown, this.isAltDown, this.isMetaDown);
/*     */ 
/* 231 */     Toolkit.getToolkit().defer(new Runnable() {
/*     */       public void run() {
/* 233 */         BaseFXRobot.out("doKeyEvent: injecting: {e}");
/* 234 */         FXRobotHelper.sceneAccessor.processKeyEvent(BaseFXRobot.this.target, localKeyEvent);
/*     */       }
/*     */     });
/* 237 */     if (this.autoWait)
/* 238 */       waitForIdle();
/*     */   }
/*     */ 
/*     */   private void doMouseEvent(double paramDouble1, double paramDouble2, MouseButton paramMouseButton, int paramInt, EventType<MouseEvent> paramEventType)
/*     */   {
/* 245 */     this.screenMouseX = (this.target.getWindow().getX() + this.target.getX() + paramDouble1);
/* 246 */     this.screenMouseY = (this.target.getWindow().getY() + this.target.getY() + paramDouble2);
/* 247 */     this.sceneMouseX = paramDouble1;
/* 248 */     this.sceneMouseY = paramDouble2;
/*     */ 
/* 250 */     MouseButton localMouseButton = paramMouseButton;
/* 251 */     Object localObject = paramEventType;
/*     */     boolean bool;
/* 252 */     if ((localObject == MouseEvent.MOUSE_PRESSED) || (localObject == MouseEvent.MOUSE_RELEASED)) {
/* 253 */       bool = localObject == MouseEvent.MOUSE_PRESSED;
/* 254 */       if (localMouseButton == MouseButton.PRIMARY)
/* 255 */         this.isButton1Pressed = bool;
/* 256 */       else if (localMouseButton == MouseButton.MIDDLE)
/* 257 */         this.isButton2Pressed = bool;
/* 258 */       else if (localMouseButton == MouseButton.SECONDARY) {
/* 259 */         this.isButton3Pressed = bool;
/*     */       }
/* 261 */       if (bool) {
/* 262 */         this.lastButtonPressed = localMouseButton;
/*     */       }
/* 264 */       else if ((!this.isButton1Pressed) && (!this.isButton2Pressed) && (!this.isButton3Pressed)) {
/* 265 */         this.lastButtonPressed = MouseButton.NONE;
/*     */       }
/*     */     }
/* 268 */     else if (localObject == MouseEvent.MOUSE_MOVED) {
/* 269 */       bool = (this.isButton1Pressed) || (this.isButton2Pressed) || (this.isButton3Pressed);
/* 270 */       if (bool) {
/* 271 */         localObject = MouseEvent.MOUSE_DRAGGED;
/* 272 */         localMouseButton = MouseButton.NONE;
/*     */       }
/*     */     }
/*     */ 
/* 276 */     final MouseEvent localMouseEvent = FXRobotHelper.inputAccessor.createMouseEvent((EventType)localObject, (int)this.sceneMouseX, (int)this.sceneMouseY, (int)this.screenMouseX, (int)this.screenMouseY, localMouseButton, paramInt, this.isShiftDown, this.isControlDown, this.isAltDown, this.isMetaDown, localMouseButton == MouseButton.SECONDARY, this.isButton1Pressed, this.isButton2Pressed, this.isButton3Pressed);
/*     */ 
/* 288 */     Toolkit.getToolkit().defer(new Runnable() {
/*     */       public void run() {
/* 290 */         BaseFXRobot.out("doMouseEvent: injecting: " + localMouseEvent);
/* 291 */         FXRobotHelper.sceneAccessor.processMouseEvent(BaseFXRobot.this.target, localMouseEvent);
/*     */       }
/*     */     });
/* 294 */     if (this.autoWait)
/* 295 */       waitForIdle();
/*     */   }
/*     */ 
/*     */   private void doScrollEvent(double paramDouble1, double paramDouble2, double paramDouble3, EventType<ScrollEvent> paramEventType)
/*     */   {
/* 302 */     this.screenMouseX = (this.target.getWindow().getX() + this.target.getX() + paramDouble1);
/* 303 */     this.screenMouseY = (this.target.getWindow().getY() + this.target.getY() + paramDouble2);
/* 304 */     this.sceneMouseX = paramDouble1;
/* 305 */     this.sceneMouseY = paramDouble2;
/*     */ 
/* 307 */     final ScrollEvent localScrollEvent = FXRobotHelper.inputAccessor.createScrollEvent(paramEventType, 0, (int)paramDouble3 * 40, ScrollEvent.HorizontalTextScrollUnits.NONE, 0, ScrollEvent.VerticalTextScrollUnits.NONE, 0, (int)this.sceneMouseX, (int)this.sceneMouseY, (int)this.screenMouseX, (int)this.screenMouseY, this.isShiftDown, this.isControlDown, this.isAltDown, this.isMetaDown);
/*     */ 
/* 317 */     Toolkit.getToolkit().defer(new Runnable() {
/*     */       public void run() {
/* 319 */         BaseFXRobot.out("doScrollEvent: injecting: " + localScrollEvent);
/* 320 */         FXRobotHelper.sceneAccessor.processScrollEvent(BaseFXRobot.this.target, localScrollEvent);
/*     */       }
/*     */     });
/* 323 */     if (this.autoWait)
/* 324 */       waitForIdle();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  73 */     String str = KeyEvent.CHAR_UNDEFINED;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.robot.impl.BaseFXRobot
 * JD-Core Version:    0.6.2
 */