/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public abstract class ButtonBase extends Labeled
/*     */ {
/*  94 */   private ReadOnlyBooleanWrapper armed = new ReadOnlyBooleanWrapper() {
/*     */     protected void invalidated() {
/*  96 */       ButtonBase.this.impl_pseudoClassStateChanged("armed");
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 101 */       return ButtonBase.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 106 */       return "armed";
/*     */     }
/*  94 */   };
/*     */ 
/* 119 */   private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 121 */       ButtonBase.this.setEventHandler(ActionEvent.ACTION, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 126 */       return ButtonBase.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 131 */       return "onAction";
/*     */     }
/* 119 */   };
/*     */ 
/* 182 */   private static final long ARMED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("armed");
/*     */ 
/*     */   public ButtonBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ButtonBase(String paramString)
/*     */   {
/*  65 */     super(paramString);
/*     */   }
/*     */ 
/*     */   public ButtonBase(String paramString, Node paramNode)
/*     */   {
/*  74 */     super(paramString, paramNode);
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty armedProperty()
/*     */   {
/*  91 */     return this.armed.getReadOnlyProperty(); } 
/*  92 */   private void setArmed(boolean paramBoolean) { this.armed.set(paramBoolean); } 
/*  93 */   public final boolean isArmed() { return armedProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty()
/*     */   {
/* 116 */     return this.onAction; } 
/* 117 */   public final void setOnAction(EventHandler<ActionEvent> paramEventHandler) { onActionProperty().set(paramEventHandler); } 
/* 118 */   public final EventHandler<ActionEvent> getOnAction() { return (EventHandler)onActionProperty().get(); }
/*     */ 
/*     */ 
/*     */   public void arm()
/*     */   {
/* 152 */     setArmed(true);
/*     */   }
/*     */ 
/*     */   public void disarm()
/*     */   {
/* 163 */     setArmed(false);
/*     */   }
/*     */ 
/*     */   public abstract void fire();
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 190 */     long l = super.impl_getPseudoClassState();
/* 191 */     if (isArmed()) l |= ARMED_PSEUDOCLASS_STATE;
/* 192 */     return l;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ButtonBase
 * JD-Core Version:    0.6.2
 */