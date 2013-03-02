/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Cursor;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.Window;
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.prism.NGNode;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ public class ViewScene extends GlassScene
/*     */ {
/*     */   private static final String UNSUPPORTED_FORMAT = "Transparent windows only supported for BYTE_BGRA_PRE format on LITTLE_ENDIAN machines";
/*     */   private View platformView;
/*     */   private PrismPen pen;
/*     */ 
/*     */   public ViewScene(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  32 */     super(paramBoolean1, paramBoolean2);
/*     */ 
/*  34 */     this.pen = new PrismPen(this, paramBoolean2);
/*  35 */     this.platformView = Application.GetApplication().createView(this.pen);
/*  36 */     this.platformView.setEventHandler(new GlassViewEventHandler(this));
/*     */   }
/*     */ 
/*     */   protected boolean isSynchronous() {
/*  40 */     if (this.pen != null) {
/*  41 */       ViewPainter localViewPainter = this.pen.getPainter();
/*  42 */       if ((localViewPainter != null) && ((localViewPainter instanceof PresentingPainter))) {
/*  43 */         return true;
/*     */       }
/*     */     }
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */   protected View getPlatformView() {
/*  50 */     return this.platformView;
/*     */   }
/*     */ 
/*     */   protected Future getPenFuture() {
/*  54 */     return this.pen.getFuture();
/*     */   }
/*     */ 
/*     */   public void uploadPixels(Pixels paramPixels) {
/*  58 */     if (this.platformView != null)
/*  59 */       this.platformView.uploadPixels(paramPixels);
/*     */   }
/*     */ 
/*     */   protected PrismPen getPen()
/*     */   {
/*  64 */     return this.pen;
/*     */   }
/*     */ 
/*     */   public void setGlassStage(GlassStage paramGlassStage)
/*     */   {
/*  69 */     super.setGlassStage(paramGlassStage);
/*     */ 
/*  72 */     if (paramGlassStage != null) {
/*  73 */       WindowStage localWindowStage = (WindowStage)paramGlassStage;
/*  74 */       Object localObject = null;
/*     */ 
/*  76 */       if (localWindowStage.needsUpdateWindow()) {
/*  77 */         if ((Pixels.getNativeFormat() != 1) || (ByteOrder.nativeOrder() != ByteOrder.LITTLE_ENDIAN))
/*     */         {
/*  79 */           throw new UnsupportedOperationException("Transparent windows only supported for BYTE_BGRA_PRE format on LITTLE_ENDIAN machines");
/*     */         }
/*  81 */         localObject = new UploadingPainter(this, this.pen);
/*     */       } else {
/*  83 */         localObject = new PresentingPainter(this, this.pen);
/*     */       }
/*  85 */       this.pen.setPainter((ViewPainter)localObject);
/*  86 */       ((ViewPainter)localObject).setRoot((NGNode)getRoot());
/*     */     }
/*     */   }
/*     */ 
/*     */   WindowStage getWindowStage() {
/*  91 */     return (WindowStage)this.glassStage;
/*     */   }
/*     */ 
/*     */   public void setScene(Object paramObject)
/*     */   {
/*  97 */     if (paramObject == null)
/*     */     {
/*  99 */       if (this.platformView != null) {
/* 100 */         this.platformView.close();
/* 101 */         this.platformView = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setRoot(PGNode paramPGNode) {
/* 107 */     super.setRoot(paramPGNode);
/* 108 */     ViewPainter localViewPainter = this.pen.getPainter();
/* 109 */     if (localViewPainter != null)
/* 110 */       localViewPainter.setRoot((NGNode)paramPGNode);
/*     */   }
/*     */ 
/*     */   public void setCamera(CameraImpl paramCameraImpl)
/*     */   {
/* 116 */     super.setCamera(paramCameraImpl);
/* 117 */     this.pen.setCamera(paramCameraImpl);
/*     */   }
/*     */ 
/*     */   public void setFillPaint(Object paramObject)
/*     */   {
/* 122 */     super.setFillPaint(paramObject);
/*     */   }
/*     */ 
/*     */   public void setCursor(final Object paramObject)
/*     */   {
/* 127 */     super.setCursor(paramObject);
/* 128 */     Application.postOnEventQueue(new Runnable()
/*     */     {
/*     */       public void run() {
/* 131 */         CursorFrame localCursorFrame = (CursorFrame)paramObject;
/* 132 */         Cursor localCursor = CursorUtils.getPlatformCursor(localCursorFrame);
/*     */ 
/* 135 */         if (ViewScene.this.platformView != null) {
/* 136 */           Window localWindow = ViewScene.this.platformView.getWindow();
/* 137 */           if (localWindow != null)
/* 138 */             localWindow.setCursor(localCursor);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   void repaint()
/*     */   {
/* 146 */     this.pen.repaint();
/*     */   }
/*     */ 
/*     */   public void enableInputMethodEvents(boolean paramBoolean)
/*     */   {
/* 151 */     this.platformView.enableInputMethodEvents(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void sceneChanged()
/*     */   {
/* 156 */     super.sceneChanged();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.ViewScene
 * JD-Core Version:    0.6.2
 */