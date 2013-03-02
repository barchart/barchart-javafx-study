/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class MenuButton extends ButtonBase
/*     */ {
/* 123 */   private final ObservableList<MenuItem> items = FXCollections.observableArrayList();
/*     */ 
/* 140 */   private ReadOnlyBooleanWrapper showing = new ReadOnlyBooleanWrapper(this, "showing", false) {
/*     */     protected void invalidated() {
/* 142 */       MenuButton.this.impl_pseudoClassStateChanged("showing");
/* 143 */       super.invalidated();
/*     */     }
/* 140 */   };
/*     */   private ObjectProperty<Side> popupSide;
/*     */   private static final String DEFAULT_STYLE_CLASS = "menu-button";
/*     */   private static final String PSEUDO_CLASS_OPENVERTICALLY = "openvertically";
/*     */   private static final String PSEUDO_CLASS_SHOWING = "showing";
/* 246 */   private static final long OPENVERTICALLY_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("openvertically");
/* 247 */   private static final long SHOWING_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("showing");
/*     */ 
/*     */   public MenuButton()
/*     */   {
/*  86 */     this(null, null);
/*     */   }
/*     */ 
/*     */   public MenuButton(String paramString)
/*     */   {
/*  97 */     this(paramString, null);
/*     */   }
/*     */ 
/*     */   public MenuButton(String paramString, Node paramNode)
/*     */   {
/* 108 */     if (paramString != null) {
/* 109 */       setText(paramString);
/*     */     }
/* 111 */     if (paramNode != null) {
/* 112 */       setGraphic(paramNode);
/*     */     }
/* 114 */     getStyleClass().setAll(new String[] { "menu-button" });
/* 115 */     setMnemonicParsing(true);
/*     */   }
/*     */ 
/*     */   public final ObservableList<MenuItem> getItems()
/*     */   {
/* 136 */     return this.items;
/*     */   }
/*     */ 
/*     */   private void setShowing(boolean paramBoolean)
/*     */   {
/* 146 */     this.showing.set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isShowing() {
/* 150 */     return this.showing.get(); } 
/* 151 */   public final ReadOnlyBooleanProperty showingProperty() { return this.showing.getReadOnlyProperty(); }
/*     */ 
/*     */ 
/*     */   public final void setPopupSide(Side paramSide)
/*     */   {
/* 169 */     popupSideProperty().set(paramSide);
/*     */   }
/*     */ 
/*     */   public final Side getPopupSide() {
/* 173 */     return this.popupSide == null ? Side.BOTTOM : (Side)this.popupSide.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Side> popupSideProperty() {
/* 177 */     if (this.popupSide == null) {
/* 178 */       this.popupSide = new ObjectPropertyBase(Side.BOTTOM) {
/*     */         protected void invalidated() {
/* 180 */           MenuButton.this.impl_pseudoClassStateChanged("openvertically");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 185 */           return MenuButton.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 190 */           return "popupSide";
/*     */         }
/*     */       };
/*     */     }
/* 194 */     return this.popupSide;
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 211 */     if ((!isDisabled()) && (!this.showing.isBound()))
/* 212 */       setShowing(true);
/*     */   }
/*     */ 
/*     */   public void hide()
/*     */   {
/* 223 */     if (!this.showing.isBound())
/* 224 */       setShowing(false);
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 233 */     fireEvent(new ActionEvent());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 254 */     long l = super.impl_getPseudoClassState();
/* 255 */     if ((getPopupSide() == Side.TOP) || (getPopupSide() == Side.BOTTOM))
/* 256 */       l |= OPENVERTICALLY_PSEUDOCLASS_STATE;
/* 257 */     if (isShowing()) {
/* 258 */       l |= SHOWING_PSEUDOCLASS_STATE;
/*     */     }
/* 260 */     return l;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MenuButton
 * JD-Core Version:    0.6.2
 */