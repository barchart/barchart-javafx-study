/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import com.sun.javafx.event.EventHandlerManager;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.scene.control.Logging;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.beans.DefaultProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ @DefaultProperty("items")
/*     */ public class Menu extends MenuItem
/*     */ {
/*  91 */   public static final EventType<Event> ON_SHOWING = new EventType(Event.ANY, "ON_SHOWING");
/*     */ 
/*  99 */   public static final EventType<Event> ON_SHOWN = new EventType(Event.ANY, "ON_SHOWN");
/*     */ 
/* 107 */   public static final EventType<Event> ON_HIDING = new EventType(Event.ANY, "ON_HIDING");
/*     */ 
/* 115 */   public static final EventType<Event> ON_HIDDEN = new EventType(Event.ANY, "ON_HIDDEN");
/*     */   private ReadOnlyBooleanWrapper showing;
/* 232 */   private ObjectProperty<EventHandler<Event>> onShowing = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 234 */       Menu.this.eventHandlerManager.setEventHandler(Menu.ON_SHOWING, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 239 */       return Menu.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 244 */       return "onShowing";
/*     */     }
/* 232 */   };
/*     */ 
/* 256 */   private ObjectProperty<EventHandler<Event>> onShown = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 258 */       Menu.this.eventHandlerManager.setEventHandler(Menu.ON_SHOWN, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 263 */       return Menu.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 268 */       return "onShown";
/*     */     }
/* 256 */   };
/*     */ 
/* 280 */   private ObjectProperty<EventHandler<Event>> onHiding = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 282 */       Menu.this.eventHandlerManager.setEventHandler(Menu.ON_HIDING, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 287 */       return Menu.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 292 */       return "onHiding";
/*     */     }
/* 280 */   };
/*     */ 
/* 304 */   private ObjectProperty<EventHandler<Event>> onHidden = new ObjectPropertyBase() {
/*     */     protected void invalidated() {
/* 306 */       Menu.this.eventHandlerManager.setEventHandler(Menu.ON_HIDDEN, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 311 */       return Menu.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 316 */       return "onHidden";
/*     */     }
/* 304 */   };
/*     */ 
/* 328 */   private final ObservableList<MenuItem> items = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<MenuItem> paramAnonymousChange)
/*     */     {
/*     */       Iterator localIterator;
/*     */       MenuItem localMenuItem;
/* 330 */       while (paramAnonymousChange.next())
/*     */       {
/* 332 */         for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localMenuItem = (MenuItem)localIterator.next();
/* 333 */           localMenuItem.setParentMenu(null);
/*     */         }
/*     */ 
/* 337 */         for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localMenuItem = (MenuItem)localIterator.next();
/* 338 */           if (localMenuItem.getParentMenu() != null) {
/* 339 */             Logging.getControlsLogger().warning("Adding MenuItem " + localMenuItem.getText() + " that has already been added to " + localMenuItem.getParentMenu().getText());
/*     */ 
/* 342 */             localMenuItem.getParentMenu().getItems().remove(localMenuItem);
/*     */           }
/*     */ 
/* 345 */           localMenuItem.setParentMenu(Menu.this);
/*     */         }
/*     */       }
/* 348 */       if ((Menu.this.getItems().size() == 0) && (Menu.this.isShowing()))
/* 349 */         Menu.this.showingPropertyImpl().set(false);
/*     */     }
/* 328 */   };
/*     */   private static final String DEFAULT_STYLE_CLASS = "menu";
/*     */   private static final String STYLE_CLASS_SHOWING = "showing";
/*     */ 
/*     */   public Menu()
/*     */   {
/* 129 */     this("");
/*     */   }
/*     */ 
/*     */   public Menu(String paramString)
/*     */   {
/* 136 */     this(paramString, null);
/*     */   }
/*     */ 
/*     */   public Menu(String paramString, Node paramNode)
/*     */   {
/* 144 */     super(paramString, paramNode);
/* 145 */     getStyleClass().add("menu");
/*     */   }
/*     */ 
/*     */   private void setShowing(boolean paramBoolean)
/*     */   {
/* 164 */     if (getItems().size() == 0) return;
/*     */ 
/* 167 */     if (paramBoolean) {
/* 168 */       if (getOnMenuValidation() != null) {
/* 169 */         Event.fireEvent(this, new Event(this.MENU_VALIDATION_EVENT));
/* 170 */         for (MenuItem localMenuItem : getItems()) {
/* 171 */           if ((!(localMenuItem instanceof Menu)) && (localMenuItem.getOnMenuValidation() != null)) {
/* 172 */             Event.fireEvent(localMenuItem, new Event(localMenuItem.MENU_VALIDATION_EVENT));
/*     */           }
/*     */         }
/*     */       }
/* 176 */       Event.fireEvent(this, new Event(ON_SHOWING));
/*     */     } else {
/* 178 */       Event.fireEvent(this, new Event(ON_HIDING));
/*     */     }
/* 180 */     showingPropertyImpl().set(paramBoolean);
/* 181 */     Event.fireEvent(this, paramBoolean ? new Event(ON_SHOWN) : new Event(ON_HIDDEN));
/*     */   }
/*     */ 
/*     */   public final boolean isShowing()
/*     */   {
/* 186 */     return this.showing == null ? false : this.showing.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty showingProperty() {
/* 190 */     return showingPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyBooleanWrapper showingPropertyImpl() {
/* 194 */     if (this.showing == null) {
/* 195 */       this.showing = new ReadOnlyBooleanWrapper()
/*     */       {
/*     */         protected void invalidated() {
/* 198 */           get();
/*     */ 
/* 201 */           if (Menu.this.isShowing())
/* 202 */             Menu.this.getStyleClass().add("showing");
/*     */           else
/* 204 */             Menu.this.getStyleClass().remove("showing");
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 210 */           return Menu.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 215 */           return "showing";
/*     */         }
/*     */       };
/*     */     }
/* 219 */     return this.showing;
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onShowingProperty()
/*     */   {
/* 224 */     return this.onShowing;
/*     */   }
/*     */ 
/*     */   public final void setOnShowing(EventHandler<Event> paramEventHandler)
/*     */   {
/* 230 */     onShowingProperty().set(paramEventHandler); } 
/* 231 */   public final EventHandler<Event> getOnShowing() { return (EventHandler)onShowingProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onShownProperty()
/*     */   {
/* 250 */     return this.onShown;
/*     */   }
/*     */ 
/*     */   public final void setOnShown(EventHandler<Event> paramEventHandler) {
/* 254 */     onShownProperty().set(paramEventHandler); } 
/* 255 */   public final EventHandler<Event> getOnShown() { return (EventHandler)onShownProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onHidingProperty()
/*     */   {
/* 274 */     return this.onHiding;
/*     */   }
/*     */ 
/*     */   public final void setOnHiding(EventHandler<Event> paramEventHandler) {
/* 278 */     onHidingProperty().set(paramEventHandler); } 
/* 279 */   public final EventHandler<Event> getOnHiding() { return (EventHandler)onHidingProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onHiddenProperty()
/*     */   {
/* 298 */     return this.onHidden;
/*     */   }
/*     */ 
/*     */   public final void setOnHidden(EventHandler<Event> paramEventHandler) {
/* 302 */     onHiddenProperty().set(paramEventHandler); } 
/* 303 */   public final EventHandler<Event> getOnHidden() { return (EventHandler)onHiddenProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final ObservableList<MenuItem> getItems()
/*     */   {
/* 367 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 375 */     if (isDisable()) return;
/* 376 */     setShowing(true);
/*     */   }
/*     */ 
/*     */   public void hide()
/*     */   {
/* 385 */     if (!isShowing()) return;
/*     */ 
/* 387 */     for (MenuItem localMenuItem : getItems()) {
/* 388 */       if ((localMenuItem instanceof Menu)) {
/* 389 */         Menu localMenu = (Menu)localMenuItem;
/* 390 */         localMenu.hide();
/*     */       }
/*     */     }
/* 393 */     setShowing(false);
/*     */   }
/*     */ 
/*     */   public <E extends Event> void addEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler) {
/* 397 */     this.eventHandlerManager.addEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public <E extends Event> void removeEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler) {
/* 401 */     this.eventHandlerManager.removeEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*     */   {
/* 406 */     return paramEventDispatchChain.prepend(this.eventHandlerManager);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Menu
 * JD-Core Version:    0.6.2
 */