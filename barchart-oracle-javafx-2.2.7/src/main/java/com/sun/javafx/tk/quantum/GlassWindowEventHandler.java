/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.glass.ui.Window;
/*    */ import com.sun.glass.ui.Window.EventHandler;
/*    */ import com.sun.javafx.tk.FocusCause;
/*    */ import com.sun.javafx.tk.TKStageListener;
/*    */ import java.security.AccessControlContext;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class GlassWindowEventHandler extends Window.EventHandler
/*    */   implements PrivilegedAction<Void>
/*    */ {
/*    */   private final GlassStage stage;
/*    */   private Window window;
/*    */   private long time;
/*    */   private int type;
/*    */ 
/*    */   public GlassWindowEventHandler(GlassStage paramGlassStage)
/*    */   {
/* 27 */     this.stage = paramGlassStage;
/*    */   }
/*    */ 
/*    */   public Void run()
/*    */   {
/* 32 */     if ((this.stage == null) || (this.stage.stageListener == null)) {
/* 33 */       return null;
/*    */     }
/* 35 */     switch (this.type) {
/*    */     case 531:
/* 37 */       this.stage.stageListener.changedIconified(true);
/* 38 */       break;
/*    */     case 532:
/* 41 */       break;
/*    */     case 533:
/* 43 */       this.stage.stageListener.changedIconified(false);
/*    */ 
/* 45 */       break;
/*    */     case 512:
/* 47 */       this.stage.stageListener.changedLocation(this.window.getX(), this.window.getY());
/* 48 */       break;
/*    */     case 511:
/* 50 */       this.stage.stageListener.changedSize(this.window.getWidth(), this.window.getHeight());
/* 51 */       break;
/*    */     case 542:
/* 53 */       GlassStage.addActiveWindow(this.stage);
/* 54 */       this.stage.stageListener.changedFocused(true, FocusCause.ACTIVATED);
/* 55 */       break;
/*    */     case 541:
/* 57 */       this.stage.stageListener.changedFocused(false, FocusCause.DEACTIVATED);
/* 58 */       break;
/*    */     case 546:
/* 60 */       this.stage.stageListener.focusUngrab();
/* 61 */       break;
/*    */     case 543:
/* 63 */       GlassStage.addActiveWindow(this.stage);
/* 64 */       this.stage.stageListener.changedFocused(true, FocusCause.TRAVERSED_FORWARD);
/* 65 */       break;
/*    */     case 544:
/* 67 */       GlassStage.addActiveWindow(this.stage);
/* 68 */       this.stage.stageListener.changedFocused(true, FocusCause.TRAVERSED_BACKWARD);
/* 69 */       break;
/*    */     case 545:
/* 71 */       this.stage.handleFocusDisabled();
/* 72 */       break;
/*    */     case 522:
/* 74 */       this.stage.setPlatformWindowClosed();
/* 75 */       this.stage.stageListener.closed();
/* 76 */       break;
/*    */     case 521:
/* 78 */       this.stage.stageListener.closing();
/*    */     case 513:
/*    */     case 514:
/*    */     case 515:
/*    */     case 516:
/*    */     case 517:
/*    */     case 518:
/*    */     case 519:
/*    */     case 520:
/*    */     case 523:
/*    */     case 524:
/*    */     case 525:
/*    */     case 526:
/*    */     case 527:
/*    */     case 528:
/*    */     case 529:
/*    */     case 530:
/*    */     case 534:
/*    */     case 535:
/*    */     case 536:
/*    */     case 537:
/*    */     case 538:
/*    */     case 539:
/* 81 */     case 540: } return null;
/*    */   }
/*    */ 
/*    */   public void handleWindowEvent(Window paramWindow, long paramLong, int paramInt)
/*    */   {
/* 86 */     this.window = paramWindow;
/* 87 */     this.time = paramLong;
/* 88 */     this.type = paramInt;
/*    */ 
/* 90 */     AccessControlContext localAccessControlContext = this.stage.getAccessControlContext();
/* 91 */     AccessController.doPrivileged(this, localAccessControlContext);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassWindowEventHandler
 * JD-Core Version:    0.6.2
 */