/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ 
/*     */ public abstract class ComboBoxBaseBuilder<T, B extends ComboBoxBaseBuilder<T, B>> extends ControlBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private boolean editable;
/*     */   private EventHandler<ActionEvent> onAction;
/*     */   private EventHandler<Event> onHidden;
/*     */   private EventHandler<Event> onHiding;
/*     */   private EventHandler<Event> onShowing;
/*     */   private EventHandler<Event> onShown;
/*     */   private String promptText;
/*     */   private T value;
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  15 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(ComboBoxBase<T> paramComboBoxBase) {
/*  18 */     super.applyTo(paramComboBoxBase);
/*  19 */     int i = this.__set;
/*  20 */     while (i != 0) {
/*  21 */       int j = Integer.numberOfTrailingZeros(i);
/*  22 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  23 */       switch (j) { case 0:
/*  24 */         paramComboBoxBase.setEditable(this.editable); break;
/*     */       case 1:
/*  25 */         paramComboBoxBase.setOnAction(this.onAction); break;
/*     */       case 2:
/*  26 */         paramComboBoxBase.setOnHidden(this.onHidden); break;
/*     */       case 3:
/*  27 */         paramComboBoxBase.setOnHiding(this.onHiding); break;
/*     */       case 4:
/*  28 */         paramComboBoxBase.setOnShowing(this.onShowing); break;
/*     */       case 5:
/*  29 */         paramComboBoxBase.setOnShown(this.onShown); break;
/*     */       case 6:
/*  30 */         paramComboBoxBase.setPromptText(this.promptText); break;
/*     */       case 7:
/*  31 */         paramComboBoxBase.setValue(this.value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B editable(boolean paramBoolean)
/*     */   {
/*  42 */     this.editable = paramBoolean;
/*  43 */     __set(0);
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */   public B onAction(EventHandler<ActionEvent> paramEventHandler)
/*     */   {
/*  53 */     this.onAction = paramEventHandler;
/*  54 */     __set(1);
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */   public B onHidden(EventHandler<Event> paramEventHandler)
/*     */   {
/*  64 */     this.onHidden = paramEventHandler;
/*  65 */     __set(2);
/*  66 */     return this;
/*     */   }
/*     */ 
/*     */   public B onHiding(EventHandler<Event> paramEventHandler)
/*     */   {
/*  75 */     this.onHiding = paramEventHandler;
/*  76 */     __set(3);
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */   public B onShowing(EventHandler<Event> paramEventHandler)
/*     */   {
/*  86 */     this.onShowing = paramEventHandler;
/*  87 */     __set(4);
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   public B onShown(EventHandler<Event> paramEventHandler)
/*     */   {
/*  97 */     this.onShown = paramEventHandler;
/*  98 */     __set(5);
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   public B promptText(String paramString)
/*     */   {
/* 108 */     this.promptText = paramString;
/* 109 */     __set(6);
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   public B value(T paramT)
/*     */   {
/* 119 */     this.value = paramT;
/* 120 */     __set(7);
/* 121 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ComboBoxBaseBuilder
 * JD-Core Version:    0.6.2
 */