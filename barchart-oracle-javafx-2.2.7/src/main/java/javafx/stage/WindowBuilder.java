/*     */ package javafx.stage;
/*     */ 
/*     */ import javafx.event.EventDispatcher;
/*     */ import javafx.event.EventHandler;
/*     */ 
/*     */ public abstract class WindowBuilder<B extends WindowBuilder<B>>
/*     */ {
/*     */   private int __set;
/*     */   private EventDispatcher eventDispatcher;
/*     */   private boolean focused;
/*     */   private double height;
/*     */   private EventHandler<WindowEvent> onCloseRequest;
/*     */   private EventHandler<WindowEvent> onHidden;
/*     */   private EventHandler<WindowEvent> onHiding;
/*     */   private EventHandler<WindowEvent> onShowing;
/*     */   private EventHandler<WindowEvent> onShown;
/*     */   private double opacity;
/*     */   private double width;
/*     */   private double x;
/*     */   private double y;
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  15 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Window paramWindow) {
/*  18 */     int i = this.__set;
/*  19 */     while (i != 0) {
/*  20 */       int j = Integer.numberOfTrailingZeros(i);
/*  21 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  22 */       switch (j) { case 0:
/*  23 */         paramWindow.setEventDispatcher(this.eventDispatcher); break;
/*     */       case 1:
/*  24 */         paramWindow.setFocused(this.focused); break;
/*     */       case 2:
/*  25 */         paramWindow.setHeight(this.height); break;
/*     */       case 3:
/*  26 */         paramWindow.setOnCloseRequest(this.onCloseRequest); break;
/*     */       case 4:
/*  27 */         paramWindow.setOnHidden(this.onHidden); break;
/*     */       case 5:
/*  28 */         paramWindow.setOnHiding(this.onHiding); break;
/*     */       case 6:
/*  29 */         paramWindow.setOnShowing(this.onShowing); break;
/*     */       case 7:
/*  30 */         paramWindow.setOnShown(this.onShown); break;
/*     */       case 8:
/*  31 */         paramWindow.setOpacity(this.opacity); break;
/*     */       case 9:
/*  32 */         paramWindow.setWidth(this.width); break;
/*     */       case 10:
/*  33 */         paramWindow.setX(this.x); break;
/*     */       case 11:
/*  34 */         paramWindow.setY(this.y);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B eventDispatcher(EventDispatcher paramEventDispatcher)
/*     */   {
/*  45 */     this.eventDispatcher = paramEventDispatcher;
/*  46 */     __set(0);
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */   public B focused(boolean paramBoolean)
/*     */   {
/*  56 */     this.focused = paramBoolean;
/*  57 */     __set(1);
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   public B height(double paramDouble)
/*     */   {
/*  67 */     this.height = paramDouble;
/*  68 */     __set(2);
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   public B onCloseRequest(EventHandler<WindowEvent> paramEventHandler)
/*     */   {
/*  78 */     this.onCloseRequest = paramEventHandler;
/*  79 */     __set(3);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public B onHidden(EventHandler<WindowEvent> paramEventHandler)
/*     */   {
/*  89 */     this.onHidden = paramEventHandler;
/*  90 */     __set(4);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public B onHiding(EventHandler<WindowEvent> paramEventHandler)
/*     */   {
/* 100 */     this.onHiding = paramEventHandler;
/* 101 */     __set(5);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   public B onShowing(EventHandler<WindowEvent> paramEventHandler)
/*     */   {
/* 111 */     this.onShowing = paramEventHandler;
/* 112 */     __set(6);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   public B onShown(EventHandler<WindowEvent> paramEventHandler)
/*     */   {
/* 122 */     this.onShown = paramEventHandler;
/* 123 */     __set(7);
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   public B opacity(double paramDouble)
/*     */   {
/* 133 */     this.opacity = paramDouble;
/* 134 */     __set(8);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public B width(double paramDouble)
/*     */   {
/* 144 */     this.width = paramDouble;
/* 145 */     __set(9);
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/* 155 */     this.x = paramDouble;
/* 156 */     __set(10);
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/* 166 */     this.y = paramDouble;
/* 167 */     __set(11);
/* 168 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.WindowBuilder
 * JD-Core Version:    0.6.2
 */