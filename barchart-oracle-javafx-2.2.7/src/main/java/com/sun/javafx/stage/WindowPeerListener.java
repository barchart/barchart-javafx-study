/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import com.sun.javafx.tk.FocusCause;
/*    */ import com.sun.javafx.tk.TKStageListener;
/*    */ import javafx.event.Event;
/*    */ import javafx.stage.Window;
/*    */ import javafx.stage.WindowEvent;
/*    */ 
/*    */ public class WindowPeerListener
/*    */   implements TKStageListener
/*    */ {
/*    */   private final Window window;
/*    */   private WindowBoundsAccessor boundsAccessor;
/*    */ 
/*    */   public WindowPeerListener(Window paramWindow)
/*    */   {
/* 46 */     this.window = paramWindow;
/*    */   }
/*    */ 
/*    */   public void setBoundsAccessor(WindowBoundsAccessor paramWindowBoundsAccessor) {
/* 50 */     this.boundsAccessor = paramWindowBoundsAccessor;
/*    */   }
/*    */ 
/*    */   public void changedLocation(float paramFloat1, float paramFloat2)
/*    */   {
/* 55 */     this.boundsAccessor.setLocation(this.window, paramFloat1, paramFloat2);
/*    */   }
/*    */ 
/*    */   public void changedSize(float paramFloat1, float paramFloat2)
/*    */   {
/* 60 */     this.boundsAccessor.setSize(this.window, paramFloat1, paramFloat2);
/*    */   }
/*    */ 
/*    */   public void changedFocused(boolean paramBoolean, FocusCause paramFocusCause)
/*    */   {
/* 65 */     this.window.setFocused(paramBoolean);
/*    */   }
/*    */ 
/*    */   public void changedIconified(boolean paramBoolean)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void changedResizable(boolean paramBoolean)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void changedFullscreen(boolean paramBoolean)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void closing()
/*    */   {
/* 82 */     Event.fireEvent(this.window, new WindowEvent(this.window, WindowEvent.WINDOW_CLOSE_REQUEST));
/*    */   }
/*    */ 
/*    */   public void closed()
/*    */   {
/* 89 */     this.window.hide();
/*    */   }
/*    */ 
/*    */   public void focusUngrab() {
/* 93 */     Event.fireEvent(this.window, new FocusUngrabEvent());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.WindowPeerListener
 * JD-Core Version:    0.6.2
 */