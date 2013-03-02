/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public abstract class ComboBoxBase<T> extends Control
/*     */ {
/*  80 */   public static final EventType<Event> ON_SHOWING = new EventType(Event.ANY, "ON_SHOWING");
/*     */ 
/*  86 */   public static final EventType<Event> ON_SHOWN = new EventType(Event.ANY, "ON_SHOWN");
/*     */ 
/*  92 */   public static final EventType<Event> ON_HIDING = new EventType(Event.ANY, "ON_HIDING");
/*     */ 
/*  98 */   public static final EventType<Event> ON_HIDDEN = new EventType(Event.ANY, "ON_HIDDEN");
/*     */ 
/* 133 */   private ObjectProperty<T> value = new SimpleObjectProperty(this, "value") {
/*     */     T oldValue;
/*     */ 
/*     */     protected void invalidated() {
/* 137 */       super.invalidated();
/* 138 */       Object localObject = get();
/*     */ 
/* 140 */       if (((this.oldValue == null) && (localObject != null)) || ((this.oldValue != null) && (!this.oldValue.equals(localObject))))
/*     */       {
/* 142 */         ComboBoxBase.this.valueInvalidated();
/*     */       }
/*     */ 
/* 145 */       this.oldValue = localObject;
/*     */     }
/* 133 */   };
/*     */ 
/* 164 */   private BooleanProperty editable = new SimpleBooleanProperty(this, "editable", false) {
/*     */     protected void invalidated() {
/* 166 */       ComboBoxBase.this.impl_pseudoClassStateChanged("editable");
/*     */     }
/* 164 */   };
/*     */   private ReadOnlyBooleanWrapper showing;
/* 216 */   private StringProperty promptText = new SimpleStringProperty(this, "promptText", "")
/*     */   {
/*     */     protected void invalidated() {
/* 219 */       String str = get();
/* 220 */       if ((str != null) && (str.contains("\n"))) {
/* 221 */         str = str.replace("\n", "");
/* 222 */         set(str);
/*     */       }
/*     */     }
/* 216 */   };
/*     */ 
/* 243 */   private BooleanProperty armed = new SimpleBooleanProperty(this, "armed", false) {
/*     */     protected void invalidated() {
/* 245 */       ComboBoxBase.this.impl_pseudoClassStateChanged("armed");
/*     */     }
/* 243 */   };
/*     */ 
/* 263 */   private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 265 */       ComboBoxBase.this.setEventHandler(ActionEvent.ACTION, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 270 */       return ComboBoxBase.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 275 */       return "onAction";
/*     */     }
/* 263 */   };
/*     */ 
/* 288 */   private ObjectProperty<EventHandler<Event>> onShowing = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 290 */       ComboBoxBase.this.setEventHandler(ComboBoxBase.ON_SHOWING, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean() {
/* 294 */       return ComboBoxBase.this;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 298 */       return "onShowing";
/*     */     }
/* 288 */   };
/*     */ 
/* 311 */   private ObjectProperty<EventHandler<Event>> onShown = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 313 */       ComboBoxBase.this.setEventHandler(ComboBoxBase.ON_SHOWN, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean() {
/* 317 */       return ComboBoxBase.this;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 321 */       return "onShown";
/*     */     }
/* 311 */   };
/*     */ 
/* 334 */   private ObjectProperty<EventHandler<Event>> onHiding = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 336 */       ComboBoxBase.this.setEventHandler(ComboBoxBase.ON_HIDING, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean() {
/* 340 */       return ComboBoxBase.this;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 344 */       return "onHiding";
/*     */     }
/* 334 */   };
/*     */ 
/* 357 */   private ObjectProperty<EventHandler<Event>> onHidden = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 359 */       ComboBoxBase.this.setEventHandler(ComboBoxBase.ON_HIDDEN, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean() {
/* 363 */       return ComboBoxBase.this;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 367 */       return "onHidden";
/*     */     }
/* 357 */   };
/*     */   private static final String DEFAULT_STYLE_CLASS = "combo-box-base";
/*     */   private static final String PSEUDO_CLASS_EDITABLE = "editable";
/*     */   private static final String PSEUDO_CLASS_SHOWING = "showing";
/*     */   private static final String PSEUDO_CLASS_ARMED = "armed";
/* 439 */   private static final long PSEUDO_CLASS_EDITABLE_MASK = StyleManager.getInstance().getPseudoclassMask("editable");
/*     */ 
/* 441 */   private static final long PSEUDO_CLASS_SHOWING_MASK = StyleManager.getInstance().getPseudoclassMask("showing");
/*     */ 
/* 443 */   private static final long PSEUDO_CLASS_ARMED_MASK = StyleManager.getInstance().getPseudoclassMask("armed");
/*     */ 
/*     */   public ComboBoxBase()
/*     */   {
/* 113 */     getStyleClass().add("combo-box-base");
/*     */   }
/*     */ 
/*     */   void valueInvalidated() {
/* 117 */     fireEvent(new ActionEvent());
/*     */   }
/*     */ 
/*     */   public ObjectProperty<T> valueProperty()
/*     */   {
/* 132 */     return this.value;
/*     */   }
/*     */ 
/*     */   public final void setValue(T paramT)
/*     */   {
/* 148 */     valueProperty().set(paramT); } 
/* 149 */   public final T getValue() { return valueProperty().get(); }
/*     */ 
/*     */ 
/*     */   public BooleanProperty editableProperty()
/*     */   {
/* 161 */     return this.editable; } 
/* 162 */   public final void setEditable(boolean paramBoolean) { editableProperty().set(paramBoolean); } 
/* 163 */   public final boolean isEditable() { return editableProperty().get(); }
/*     */ 
/*     */ 
/*     */   public ReadOnlyBooleanProperty showingProperty()
/*     */   {
/* 177 */     return showingPropertyImpl().getReadOnlyProperty(); } 
/* 178 */   public final boolean isShowing() { return showingPropertyImpl().get(); }
/*     */ 
/*     */   private void setShowing(boolean paramBoolean) {
/* 181 */     Event.fireEvent(this, paramBoolean ? new Event(ON_SHOWING) : new Event(ON_HIDING));
/*     */ 
/* 183 */     showingPropertyImpl().set(paramBoolean);
/* 184 */     Event.fireEvent(this, paramBoolean ? new Event(ON_SHOWN) : new Event(ON_HIDDEN));
/*     */   }
/*     */ 
/*     */   private ReadOnlyBooleanWrapper showingPropertyImpl() {
/* 188 */     if (this.showing == null) {
/* 189 */       this.showing = new ReadOnlyBooleanWrapper(false) {
/*     */         protected void invalidated() {
/* 191 */           ComboBoxBase.this.impl_pseudoClassStateChanged("showing");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 196 */           return ComboBoxBase.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 201 */           return "showing";
/*     */         }
/*     */       };
/*     */     }
/* 205 */     return this.showing;
/*     */   }
/*     */ 
/*     */   public final StringProperty promptTextProperty()
/*     */   {
/* 226 */     return this.promptText; } 
/* 227 */   public final String getPromptText() { return (String)this.promptText.get(); } 
/* 228 */   public final void setPromptText(String paramString) { this.promptText.set(paramString); }
/*     */ 
/*     */ 
/*     */   public BooleanProperty armedProperty()
/*     */   {
/* 240 */     return this.armed; } 
/* 241 */   private final void setArmed(boolean paramBoolean) { armedProperty().set(paramBoolean); } 
/* 242 */   public final boolean isArmed() { return armedProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty()
/*     */   {
/* 260 */     return this.onAction; } 
/* 261 */   public final void setOnAction(EventHandler<ActionEvent> paramEventHandler) { onActionProperty().set(paramEventHandler); } 
/* 262 */   public final EventHandler<ActionEvent> getOnAction() { return (EventHandler)onActionProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onShowingProperty()
/*     */   {
/* 281 */     return this.onShowing;
/*     */   }
/*     */ 
/*     */   public final void setOnShowing(EventHandler<Event> paramEventHandler)
/*     */   {
/* 286 */     onShowingProperty().set(paramEventHandler); } 
/* 287 */   public final EventHandler<Event> getOnShowing() { return (EventHandler)onShowingProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onShownProperty()
/*     */   {
/* 304 */     return this.onShown;
/*     */   }
/*     */ 
/*     */   public final void setOnShown(EventHandler<Event> paramEventHandler)
/*     */   {
/* 309 */     onShownProperty().set(paramEventHandler); } 
/* 310 */   public final EventHandler<Event> getOnShown() { return (EventHandler)onShownProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onHidingProperty()
/*     */   {
/* 327 */     return this.onHiding;
/*     */   }
/*     */ 
/*     */   public final void setOnHiding(EventHandler<Event> paramEventHandler)
/*     */   {
/* 332 */     onHidingProperty().set(paramEventHandler); } 
/* 333 */   public final EventHandler<Event> getOnHiding() { return (EventHandler)onHidingProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onHiddenProperty()
/*     */   {
/* 350 */     return this.onHidden;
/*     */   }
/*     */ 
/*     */   public final void setOnHidden(EventHandler<Event> paramEventHandler)
/*     */   {
/* 355 */     onHiddenProperty().set(paramEventHandler); } 
/* 356 */   public final EventHandler<Event> getOnHidden() { return (EventHandler)onHiddenProperty().get(); }
/*     */ 
/*     */ 
/*     */   public void show()
/*     */   {
/* 385 */     if (!isDisabled())
/* 386 */       setShowing(true);
/*     */   }
/*     */ 
/*     */   public void hide()
/*     */   {
/* 394 */     if (isShowing())
/* 395 */       setShowing(false);
/*     */   }
/*     */ 
/*     */   public void arm()
/*     */   {
/* 408 */     if (!armedProperty().isBound())
/* 409 */       setArmed(true);
/*     */   }
/*     */ 
/*     */   public void disarm()
/*     */   {
/* 421 */     if (!armedProperty().isBound())
/* 422 */       setArmed(false);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 451 */     long l = super.impl_getPseudoClassState();
/* 452 */     if (isEditable()) l |= PSEUDO_CLASS_EDITABLE_MASK;
/* 453 */     if (isShowing()) l |= PSEUDO_CLASS_SHOWING_MASK;
/* 454 */     if (isArmed()) l |= PSEUDO_CLASS_ARMED_MASK;
/* 455 */     return l;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ComboBoxBase
 * JD-Core Version:    0.6.2
 */